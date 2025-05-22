package app.controllers;

import app.DTO.UserDTO;
import app.DTO.UserOrderDTO;
import app.entities.Order;
import app.entities.User;
import app.exceptions.DatabaseException;
import app.persistence.ConnectionPool;
import app.persistence.OrderMapper;
import app.persistence.UserMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;

import java.util.ArrayList;
import java.util.List;

public class OrderController {
    public static void addRouts(Javalin app, ConnectionPool connectionPool){
        app.get("/adminPage1", ctx -> watchOrders(ctx, connectionPool));
    }

    public static void watchOrders(Context ctx, ConnectionPool connectionPool){
        try {
            List<User> users = UserMapper.getAllUsers(connectionPool);
            List<UserOrderDTO> userOrders = new ArrayList<>();

            for(User u: users ){
                // for each user object is a list of the specific user with the specific orders
                List<Order> orders = OrderMapper.getOrdersByUserId(connectionPool, u.getUserId());
                for(Order o: orders){
                    UserOrderDTO userOrderInfo = new UserOrderDTO(u.getUserId(), o.getOrderId(), u.getUserName(),
                            u.getUserEmail(), u.getUserTlf(), u.getAddress(),
                            o.getLength(), o.getWidth());
                    userOrders.add(userOrderInfo);
                }

            }
            ctx.attribute("userOrders", userOrders);
            ctx.attribute("user", users);
            ctx.render("adminPage1.html");

        }catch(DatabaseException e){
            ctx.status(500);
            ctx.attribute("message", "Failed trying to get the users order data");
            ctx.render("adminPage1.html");
        }
    }
}
