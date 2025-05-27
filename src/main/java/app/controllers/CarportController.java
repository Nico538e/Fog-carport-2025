package app.controllers;

import app.entities.Order;
import app.entities.User;
import app.persistence.ConnectionPool;
import app.persistence.OrderMapper;
import app.services.Calculator;
import app.services.CarportSvg;
import io.javalin.Javalin;
import io.javalin.http.Context;
import okhttp3.*;

import java.io.IOException;
import java.util.Locale;

public class CarportController {

    public static void addRoutes(Javalin app, ConnectionPool connectionPool){
        app.get("/carportInfo", ctx -> CarportController.showSvgDrawing(ctx));
        app.get("/inspiration", ctx -> ctx.render("inspiration.html"));
        app.get("/aboutOurCarports", ctx -> ctx.render("aboutOurCarports.html"));
        app.get("/goDesign", ctx -> ctx.render("designCarport.html"));
        app.get("/designCarport", ctx -> ctx.render("designCarport.html"));
        app.get("/materialList", ctx -> calculateOrderLines(ctx, connectionPool));
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
                        "Nu kan du til en hver tid logge ind og tjekke din forespørgelse: \n\n" +
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

            int orderId = Integer.parseInt(ctx.queryParam("orderId"));
            Order order = OrderMapper.getOrderById(orderId, connectionPool);

            boolean isOrderOwner = currentUser.getUserId() == order.getUserId();
            boolean isPaid = order.isPaid();
            boolean isAdmin = currentUser.getRole().equalsIgnoreCase("admin");

            //Hvis kundens forespørgsel ikke er betalt må de ikke se styklisten/costumerPage
            if (!(( isOrderOwner && isPaid ) || isAdmin)) {
                ctx.result("You are not authorized to view this page.");
                return;
            }

            Calculator calculator = new Calculator(connectionPool);
            calculator.calculate(order);

            ctx.attribute("orderLines", calculator.getOrderLines());
            ctx.attribute("order", order);
            ctx.attribute("currentUser", currentUser);

            ctx.render("materialList.html");
        }catch (Exception e){
            e.printStackTrace();
            ctx.attribute("message", "Fejl ved beregning: " + e.getMessage());
            ctx.render("index.html");
        }
    }
}
