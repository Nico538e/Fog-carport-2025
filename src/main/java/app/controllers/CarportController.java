package app.controllers;

import app.entities.Orders;
import app.entities.User;
import app.exceptions.DatabaseException;
import app.persistence.ConnectionPool;
import app.persistence.OrderMapper;
import app.services.Calculator;
import app.services.CarportSvg;
import app.services.Svg;
import io.javalin.Javalin;
import io.javalin.http.Context;
import app.persistence.UserMapper;
import okhttp3.*;
import java.io.IOException;

import java.util.List;
import java.util.Locale;

public class CarportController {

    public static void addRoutes(Javalin app, ConnectionPool connectionPool){
        app.get("/carportInfo", ctx -> CarportController.showSvgDrawing(ctx));
        app.get("/inspiration", ctx -> ctx.redirect(""));
        app.get("/aboutOurCarports", ctx -> ctx.render("aboutOurCarports.html"));
        app.get("/goDesign", ctx -> ctx.render("designCarport.html"));
        app.get("/designCarport", ctx -> ctx.render("designCarport.html"));
        app.get("/matrialeList", ctx-> calculateOrderLines(ctx,connectionPool));
    }

    public static void checkAllOrders(Context ctx, ConnectionPool connectionPool){
        try {
            List<Orders> orders = UserMapper.getOrdersByUserId(connectionPool,2);

            ctx.attribute("orders", orders);
            ctx.render("page2.html"); //change filepath

        } catch (DatabaseException e) {
            ctx.status(500);
            ctx.attribute("message", "Fejl ved hentning af order data");
            ctx.render("error.html"); //change filepath
        }
    }


    public static void mailSender(Context ctx, ConnectionPool connectionPool, User user, String password) throws IOException{
        String domainName = "mg.kodekriger.dk";
        String apikey =  System.getenv("MAILGUN_API_KEY");

        OkHttpClient client = new OkHttpClient();


        RequestBody formbody = new FormBody.Builder()
                .add("from","noreply@" + domainName)
                .add("to", user.getUserEmail())
                .add("subject", "Bestilling af carport")
                .add("text", "Tak for din interesse, \n" +
                        "her er dit password, som du kan logge ind med den mail du sendte ind: \n\n" +
                        "Mail: " + user.getUserEmail() + "\n" +
                        "Password: " + password + "\n\n" )
                .build();

        Request request = new Request.Builder()
                .url("https://api.eu.mailgun.net/v3/" + domainName + "/messages")
                .post(formbody)
                .addHeader("Authorization", Credentials.basic("api", apikey))
                .build();


        try(Response response = client.newCall(request).execute()){
            if(response.isSuccessful()) {
                System.out.println("Email sendt " + response.body().string());
            }else{
                System.out.println("Fejl ved afsendelse " + response.body().string());
            }
        }
    }

    public static void showSvgDrawing(Context ctx){
        Locale.setDefault(new Locale("US")); //Setting the number sustem på US(amarican)
        // Gets the length and width parameters form Url
        int length = ctx.queryParamAsClass("length",Integer.class).getOrDefault(780);
        int width =ctx.queryParamAsClass("width",Integer.class).getOrDefault(600);
        //Create the SVG, with customers length and width
        CarportSvg svg = new CarportSvg(width,length);
        //Add SVG to the render
        ctx.attribute("svg",svg.toString());
        ctx.render("designCarportInfo.html");
    }

    //styklisteberegner
    public static void calculateOrderLines(Context ctx, ConnectionPool connectionPool){
        try{
           User currentUser = ctx.sessionAttribute("currentUser");
            if (currentUser == null) {
                ctx.redirect("/login"); // eller vis en fejl
                return;
            }

           Orders order;

            if(currentUser.getRole().equalsIgnoreCase("admin")){
                int orderId = Integer.parseInt(ctx.queryParam("orderId"));
                order = OrderMapper.getOrderById(orderId, connectionPool);
            }else{
                order = OrderMapper.getLatestPaidOrderByUserId(currentUser.getUserId(), connectionPool);
            }

            Calculator calculator =new Calculator(connectionPool);
            calculator.calculate(order);

            ctx.attribute("orderLines", calculator.getOrderLines());
            ctx.attribute("order", order);
            ctx.attribute("currentUser", currentUser);

            ctx.render("matrialeList.html");
        }catch (Exception e){
            e.printStackTrace();
            ctx.attribute("message", "Fejl ved beregning: " + e.getMessage());
            ctx.render("index.html");
        }
    }


}
