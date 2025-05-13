package app.controllers;

import app.entities.Orders;
import app.exceptions.DatabaseException;
import app.persistence.ConnectionPool;
import io.javalin.Javalin;
import io.javalin.http.Context;
import app.persistence.UserMapper;

import java.util.List;

public class CarportController {

    public static void addRoutes(Javalin app, ConnectionPool connectionPool){


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


    // minder om showCart fra cupcake
    public static void showUserOrder(){

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
