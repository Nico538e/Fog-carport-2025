package app.controllers;

import app.entities.Orders;
import app.exceptions.DatabaseException;
import app.persistence.ConnectionPool;
import io.javalin.Javalin;
import io.javalin.http.Context;
import app.persistence.UserMapper;
import okhttp3.*;
import java.io.IOException;

import java.util.List;

public class CarportController {

    public static void addRoutes(Javalin app, ConnectionPool connectionPool){
        app.get("login", ctx -> ctx.render("login.html"));
        app.get("AboutOurCarports", ctx -> ctx.render("aboutOurCarports.html"));
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



    public static void MailSender() throws IOException{
        String domainName = "mg.kodekriger.dk";
        String apikey =  System.getenv("MAILGUN_API_KEY");

        OkHttpClient client = new OkHttpClient();


        RequestBody formbody = new FormBody.Builder()
                .add("from","noreply@" + domainName)
                .add("to","nicolai-strand@hotmail.com")
                .add("subject", "Bestilling af carport")
                .add("text", "Tak for din bestilling")
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
//TODO: create a svg drawing and inject into designCarport.html as a String

        String svgText = "<svg width=\"127\" height=\"105\" viewBox=\"0 0 255 210\">\n" +
                "    <rect x=\"0\" y=\"0\" height=\"210\" width=\"255\"\n" +
                "          style=\"stroke:#000000; fill: #f7cb02\"/>\n" +
                "    <rect x=\"0\" y=\"0\" height=\"90\" width=\"90\"\n" +
                "          style=\"stroke:#000000; fill: #2d6caa\"/>\n" +
                "    <rect x=\"120\" y=\"0\" height=\"90\" width=\"135\"\n" +
                "          style=\"stroke:#000000; fill: #2d6caa\"/>\n" +
                "    <rect x=\"0\" y=\"120\" height=\"90\" width=\"90\"\n" +
                "          style=\"stroke:#000000; fill: #2d6caa\"/>\n" +
                "    <rect x=\"120\" y=\"120\" height=\"90\" width=\"135\"\n" +
                "          style=\"stroke:#000000; fill: #2d6caa\"/>\n" +
                "\n" +
                "</svg>";

        ctx.attribute("svg",svgText);
        ctx.render("designCarport.html");
    }



}
