package app.controllers;

import app.entities.ShowUserOrders;
import app.entities.User;
import app.exceptions.DatabaseException;
import app.persistence.ConnectionPool;
import app.persistence.UserMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class UserController {
    public static void addRoutes(Javalin app, ConnectionPool connectionPool){
        app.get("/adminWatchOrders", ctx -> UserController.watchOrders(ctx, connectionPool));
        app.get("/adminPage1", ctx -> UserController.watchOrders(ctx, connectionPool));
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
                ctx.redirect("/admin");
            } else {
                ctx.redirect("/");
            }

            //session atribute hvis du kommer fra basket ligesom user
            //if statement med om du komer fra basket
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
            List<ShowUserOrders> userOrder = UserMapper.adminGetUserWithOrders(connectionPool);

            ctx.attribute("userOrder", userOrder);
            ctx.render("adminPage1.html");


        }catch(DatabaseException e){
            ctx.status(500);
            ctx.attribute("message", "Failed trying to get the users order data");
            ctx.render("error.html");
        }

    }
}
