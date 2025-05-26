package app.controllers;

import app.entities.Order;
import app.entities.User;
import app.exceptions.DatabaseException;
import app.persistence.ConnectionPool;
import app.persistence.OrderMapper;
import app.persistence.UserMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.List;

import static app.controllers.CarportController.calculateOrderLines;

public class UserController {
    public static void addRoutes(Javalin app, ConnectionPool connectionPool) {
        app.post("/adminPage2", ctx -> UserController.getUserOrderForm(ctx, connectionPool));
        app.post("/saveChangeAdminPage1", ctx -> UserController.updateUserInfo(ctx, connectionPool));

        app.post("/login", ctx -> login(ctx, connectionPool));
        app.get("login", ctx -> ctx.render("login.html"));
        app.get("logout", ctx -> logout(ctx));
        app.post("/addUser", ctx-> createUser(ctx, connectionPool));
        app.post("/designCarport", ctx -> handleOrderSelections(ctx));
        app.get("/costumerPage", ctx -> showCostumerPage(ctx,connectionPool));
    }

    public static void login(Context ctx, ConnectionPool connectionPool) {
        // Hent email og password fra login-formularen
        String email = ctx.formParam("email");
        String password = ctx.formParam("password");

        try {
            User user = UserMapper.login(email, password, connectionPool); //Tjekker om brugeren findes i databasen
            if (user == null) {
                ctx.attribute("message", "forkert email eller login");
                ctx.render("login.html");
                return;
            }

            ctx.sessionAttribute("currentUser", user);//gemmer brugeren i sessionen(så man ikke skal logge ind på hver side)

            //check if you are admin and sending it to admin front page
            if (user.getRole().equals("admin")) {
                ctx.redirect("/adminPage1");
            } else {
                ctx.redirect("/costumerPage");
            }

        } catch (DatabaseException e){ // hvis login failer(forkert email eller password), kommer denne besked og siden rendere igen
            ctx.attribute("message", "Forkert email eller password");
            ctx.render("login.html");
            e.printStackTrace();
        }
    }

    private static void logout(@NotNull Context ctx) {
        ctx.req().getSession().invalidate();// Invaliderer den aktuelle session (sletter session-data, f.eks. brugerens login-information)

        ctx.redirect("/");// Omdirigerer brugeren til forsiden af applikationen
    }

    public static void getUserOrderForm(Context ctx, ConnectionPool connectionPool) {
        int userId = Integer.parseInt(ctx.formParam("userId"));

        try{
            User user = UserMapper.adminGetUserDataByUserid(connectionPool, userId);
            ctx.attribute("user", user);
            ctx.render("adminPage2.html");

        }catch(DatabaseException e){
            ctx.status(500);
            ctx.attribute("message", "Failed could not get the user information and price of order");
            ctx.render("adminPage1.html");
        }
    }


    public static void updateUserInfo(Context ctx, ConnectionPool connectionPool) throws DatabaseException {
        int userId = Integer.parseInt(ctx.formParam("userId"));

        try{
            String userName = ctx.formParam("userName");
            String userEmail = ctx.formParam("userEmail");
            String userTlf = ctx.formParam("userTlf");
            String address = ctx.formParam("address");

            //The existing user data
            User userExists = UserMapper.adminGetUserDataByUserid(connectionPool, userId);

            if(userName == null){
                userName = userExists.getUserName();
            }

            if(userEmail == null){
                userEmail = userExists.getUserEmail();
            }

            if(userTlf == null){
                userTlf = String.valueOf(userExists.getUserTlf());
            }

            if(address == null){
                address = userExists.getAddress();
            }

            // updater user info by comparing user and already existing userData
            if(!userName.equals(userExists.getUserName())){
                UserMapper.updateUserName(connectionPool, userName, userId);
            }

            if(!userEmail.equals(userExists.getUserEmail())){
                UserMapper.updateUserEmail(connectionPool, userEmail, userId);
            }

            int tlf = Integer.parseInt(userTlf);
            if(tlf != userExists.getUserTlf()){
                UserMapper.updateUserTlf(connectionPool, tlf, userId);
            }

            if(!address.equals(userExists.getAddress())){
                UserMapper.updateUserAddress(connectionPool, address, userId);
            }

            ctx.redirect("/adminPage1");

        }catch(DatabaseException e){
            ctx.status(500);
            ctx.attribute("message", "Failed trying to update");
            ctx.render("adminPage1.html");
        } catch (NumberFormatException e){
            ctx.status(400);
            ctx.attribute("message", "Failed wrong format in price og tel-number");
            ctx.render("adminPage2.html");
        }
    }

    public static void createUser(Context ctx, ConnectionPool connectionPool) throws IOException {
        try{
            String name = ctx.formParam("name");
            String email = ctx.formParam("email");
            String address = ctx.formParam("address");
            String tlfNr = ctx.formParam("telephone");

            if(name == null|| email == null||address == null || tlfNr == null){
                ctx.attribute("message", " Alle felter skal udfyldes");
                ctx.render("designCarportInfo.html");
                return;
            }

            int phone = Integer.parseInt(tlfNr);

            //auto password
            String autoPassword = generateRandomPassword(8);

            //Standart values
            String role = "postgres";
            boolean isPaidStatus = false;

            // check if user exist
            List<User> checkAllUsers = UserMapper.getUserNamesAndUserId(connectionPool, "postgres");
            boolean exists = checkAllUsers.stream().anyMatch(u-> u.getUserEmail()!=null && u.getUserEmail().equalsIgnoreCase(email));

            if (exists){
                ctx.attribute("message", "En bruger med denne email findes allerede.");
                ctx.render("designCarportInfo.html");
                return;
            }

            User user = new User(name, autoPassword, role, email, phone, address);
            UserMapper.createNewUser(user, connectionPool);

            User createdUser = UserMapper.getUserByEmail(connectionPool, email);

            String carportLength = ctx.sessionAttribute("length");
            String carportWidth = ctx.sessionAttribute("width");

            if(carportLength != null && carportWidth != null){

                int length = Integer.parseInt(carportLength);
                int width = Integer.parseInt(carportWidth);
                OrderMapper.createOrders(connectionPool, createdUser.getUserId(), length, width);

            }

            CarportController.mailSender(ctx, connectionPool, createdUser, autoPassword);
            ctx.attribute("message", "Din forespørgelse er nu oprettet, du vil blive kontaktet snarest");
            ctx.render("designCarportInfo.html");


        } catch (DatabaseException e) {
            ctx.status(500);
            ctx.attribute("message", "Der opstod en fejl med oprettelsen af forspørgelsen" + e.getMessage());
            ctx.render("designCarportInfo.html");
        }
    }

    //for at håndtere hvad kunden vælger af længder og bredder
    public static void handleOrderSelections(Context ctx){
        String length = ctx.formParam("length");
        String width = ctx.formParam("width");

        if(length.equals("length") || width.equals("width")){
            ctx.attribute("message", "Vælg både længde og bredde");
            ctx.render("designCarport.html");
            return;
        }

        ctx.sessionAttribute("length", length);
        ctx.sessionAttribute("width", width);

        ctx.redirect("/carportInfo");
    }

    public static String generateRandomPassword(int length) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int index = (int) (Math.random() * chars.length());
            sb.append(chars.charAt(index));
        }
        return sb.toString();
    }
    public static void showCostumerPage(Context ctx, ConnectionPool connectionPool) throws IOException, DatabaseException {
        User currentUser = ctx.sessionAttribute("currentUser");
        int orderId = 3;
        // int orderId = Integer.parseInt(ctx.sessionAttribute("orderId"));

        if (currentUser == null) {
            ctx.redirect("/login");
            return;
        }

        // Hent ordre til brugeren (hvis en ordre eksisterer)
        Order order = OrderMapper.getOrderById(orderId,connectionPool);

        ctx.attribute("currentUser", currentUser);
        ctx.attribute("order", order);

        ctx.render("costumerPage.html");
    }

}
