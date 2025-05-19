package app.controllers;

import app.entities.User;
import app.exceptions.DatabaseException;
import app.persistence.ConnectionPool;
import app.persistence.UserMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;
import org.jetbrains.annotations.NotNull;

import java.lang.invoke.StringConcatException;
import java.util.List;

public class UserController {
    public static void addRoutes(Javalin app, ConnectionPool connectionPool) {
        app.get("adminPage2", ctx -> UserController.watchOrders(ctx, connectionPool));
        app.get("/adminPage1", ctx -> UserController.watchOrders(ctx, connectionPool));
        app.post("/login", ctx -> login(ctx, connectionPool));
        app.get("login", ctx -> ctx.render("login.html"));
        app.get("logout", ctx -> logout(ctx));
        app.get("/carportInfo", ctx -> ctx.render("designCarportInfo.html"));
        app.post("/addUser", ctx-> createUser(ctx, connectionPool));
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
                ctx.redirect("/");
            }

        } catch (DatabaseException e){ // hvis login failer(forkert email eller password), kommer denne besked og siden rendere igen
            ctx.attribute("message", "Forkert email eller password");
            ctx.render("login.html");
        }
    }

    // Metode til at logge brugeren ud
    private static void logout(@NotNull Context ctx) {
        ctx.req().getSession().invalidate();// Invaliderer den aktuelle session (sletter session-data, f.eks. brugerens login-information)

        ctx.redirect("/");// Omdirigerer brugeren til forsiden af applikationen
    }

    public static void getUserOptions(Context ctx, ConnectionPool connectionPool) {
        try {
            List<User> users = UserMapper.getAllUsers(connectionPool, "postgres");

            ctx.attribute("users", users);
            ctx.render("index.html"); //change filepath

        } catch (DatabaseException e) {
            ctx.status(500);
            ctx.attribute("message", "Fejl ved hentning af user data");
            ctx.render("index.html"); //change filepath
        }
    }

    public static void watchOrders(Context ctx, ConnectionPool connectionPool){
        try {
            List<User> userOrder = UserMapper.adminGetUserWithOrders(connectionPool);

            ctx.attribute("userOrder", userOrder);
            ctx.render("adminPage1.html");



        }catch(DatabaseException e){
            ctx.status(500);
            ctx.attribute("message", "Failed trying to get the users order data");
            ctx.render("error.html");
        }
    }

    public static void createUser(Context ctx, ConnectionPool connectionPool){
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
            List<User> checkAllUsers = UserMapper.getAllUsers(connectionPool, "postgres");
            boolean exists = checkAllUsers.stream().anyMatch(u-> u.getUserEmail()!=null && u.getUserEmail().equalsIgnoreCase(email));

            if (exists){
                ctx.attribute("message", "En bruger med denne email findes allerede.");
                ctx.render("designCarportInfo.html");
                return;
            }

            User user = new User(name, autoPassword, role, email, phone, isPaidStatus, address);
            UserMapper.createNewUser(user, connectionPool);
            ctx.attribute("message", "Din forespørgelse er nu oprettet, du vil blive kontaktet snartes");
            ctx.render("designCarportInfo");


        } catch (DatabaseException e) {
            ctx.status(500);
            ctx.attribute("message", "Der opsod en fejl me doprettelsen af forspørgelsen" + e.getMessage());
            ctx.render("designCarportInfo.html");
        }
    }

    private static String generateRandomPassword(int length) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int index = (int) (Math.random() * chars.length());
            sb.append(chars.charAt(index));
        }
        return sb.toString();
    }

}
