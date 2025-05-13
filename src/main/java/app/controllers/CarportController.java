package app.controllers;

import app.entities.Orders;
import app.exceptions.DatabaseException;
import app.persistence.ConnectionPool;
import app.services.CarportSvg;
import app.services.Svg;
import io.javalin.Javalin;
import io.javalin.http.Context;
import app.persistence.UserMapper;

import java.util.List;
import java.util.Locale;

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
        Locale.setDefault(new Locale("US")); //Setting the number sustem på US(amarican)
        int length = ctx.queryParamAsClass("length",Integer.class).getOrDefault(780);
        int width =ctx.queryParamAsClass("width",Integer.class).getOrDefault(600);
        CarportSvg svg = new CarportSvg(width,length);
        ctx.attribute("svg",svg.toString());
        ctx.render("designCarport.html");
    }


}
