package app.persistence;

import app.entities.Orders;
import app.entities.User;
import app.exceptions.DatabaseException;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserMapper {
    public static User login(String userName, String password, ConnectionPool connectionPool) throws DatabaseException {
        String sql = "select * from users where user_name=? and user_password=?";

        try (
                Connection connection = connectionPool.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)
        ) {
            ps.setString(1, userName);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int userId = rs.getInt("user_id");
                String roles = rs.getString("role");
                String userPassword = rs.getString("user_password");
                String userEmail = rs.getString("user_email");
                int userTlf = rs.getInt("user_tlf");
                boolean isPaidStatus = rs.getBoolean("isPaidStatus");
                String address = rs.getString("address");
                return new User( userId, userName, userPassword, roles, userEmail, userTlf, isPaidStatus, address) ;
            } else {
                throw new DatabaseException("Fejl i login. Prøv igen");
            }
        } catch (SQLException e) {
            throw new DatabaseException("DB fejl", e.getMessage());
        }
    }
    public static List<User> getAllUsers(ConnectionPool connectionPool, String role) throws DatabaseException {
        List<User> userNameList = new ArrayList<>();
        String sql = "SELECT user_id, user_name  FROM users where role = 'postgres'";


        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {



            while (rs.next()) {
                int userId = rs.getInt("user_id");
                String userName = rs.getString("user_name");

                User users = new User(userId,userName);
                userNameList.add(users);
            }

        } catch (SQLException e) {
            throw new DatabaseException("Failed trying to get all userNames", e);
        }
        return userNameList;
    }

    public static List<Orders> getOrdersByUserId(ConnectionPool connectionPool, int userId) throws DatabaseException{
        List<Orders> ordersList = new ArrayList<>();
        String sql = "SELECT * FROM orders Where user_id = ?";

        try(Connection connection = connectionPool.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql)
        ){
            ps.setInt(1,userId);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                int orderId = rs.getInt("order_id");
                Orders order = new Orders(orderId,userId);
                ordersList.add(order);
            }

        }catch(SQLException e){
            throw new DatabaseException("Failed could not get the order from the specific user: " + userId, e);
        }
        return ordersList;
    }


    public static List<User> adminGetUserOrdersById(ConnectionPool connectionPool, int userId) throws DatabaseException{
        List<User> userOrderList = new ArrayList<>();

        String sql = "SELECT o.order_id, " +
                "u.user_name, " +
                "u.user_email, " +
                "u.user_tlf, " +
                "u.user_address, " +
                "u.is_paid_status, " +
                "ol.cost_price " +
                "FROM orders o" +
                "join users u on o.user_id = u.user_id " +
                "join order_line ol on o.order_id = ol.order_id " +
                "where u.role = 'postgres' " +
                "and o.order_id is not null " +
                "and u.user_id = ?";

        try(Connection connection = connectionPool.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql)
        ){
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                int orderId = rs.getInt("order_id");
                String userName = rs.getString("user_name");
                String email = rs.getString("user_email");
                int tlf = rs.getInt("user_tlf");
                String address = rs.getString("user_address");
                boolean isPaidStatus = rs.getBoolean("is_paid_status");
                BigDecimal costPrice = rs.getBigDecimal("cost_price");

                User users = new User(orderId, userName, email, tlf, address, isPaidStatus, costPrice);
                userOrderList.add(users);
            }

        }catch(SQLException e){
            throw new DatabaseException("Failed could not get the order and user details of the user: " + userId, e);
        }
        return userOrderList;
    }

}
