package app.persistence;


import app.DTO.UserDTO;
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
        String sql = "select * from users where user_email=? and user_password=?";

        try (
                Connection connection = connectionPool.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)

        ) {
            ps.setString(1, userName);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int userId = rs.getInt("user_id");
                String user_name = rs.getString("user_name");
                String roles = rs.getString("role");
                String userPassword = rs.getString("user_password");
                String userEmail = rs.getString("user_email");
                int userTlf = rs.getInt("user_tlf");
                boolean isPaidStatus = rs.getBoolean("is_paid_status");
                String address = rs.getString("user_address");
                return new User(userId, userName, userPassword, roles, userEmail, userTlf, isPaidStatus, address);
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

                User users = new User(userId, userName);
                userNameList.add(users);
            }

        } catch (SQLException e) {
            throw new DatabaseException("Failed trying to get all userNames", e);
        }
        return userNameList;
    }

    public static List<Orders> getOrdersByUserId(ConnectionPool connectionPool, int userId) throws DatabaseException {
        List<Orders> ordersList = new ArrayList<>();
        String sql = "SELECT * FROM orders Where user_id = ?";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)
        ) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int orderId = rs.getInt("order_id");
                Orders order = new Orders(orderId, userId);
                ordersList.add(order);
            }

        } catch (SQLException e) {
            throw new DatabaseException("Failed could not get the order from the specific user: " + userId, e);
        }
        return ordersList;
    }

    public static List<User> adminGetUserWithOrders(ConnectionPool connectionPool) throws DatabaseException {
        List<User> userOrderList = new ArrayList<>();

        String sql = "SELECT o.order_id, " +
                "u.user_name, " +
                "u.user_email, " +
                "u.user_tlf, " +
                "u.user_address, " +
                "u.is_paid_status, " +
                "ol.cost_price " +
                "FROM orders o " +
                "join users u on o.user_id = u.user_id " +
                "join order_line ol on o.order_id = ol.order_id " +
                "where u.role = 'postgres' " +
                "and o.order_id is not null ";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)
        ) {
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int orderId = rs.getInt("order_id");
                String userName = rs.getString("user_name");
                String userEmail = rs.getString("user_email");
                int tlf = rs.getInt("user_tlf");
                String address = rs.getString("user_address");
                boolean isPaidStatus = rs.getBoolean("is_paid_status");
                BigDecimal costPrice = rs.getBigDecimal("cost_price");

                User showUserOrders = new User(orderId, userName, userEmail, tlf, address, isPaidStatus, costPrice);
                userOrderList.add(showUserOrders);
            }

        } catch (SQLException e) {
            System.out.println("Failed could not get the order and user details of the user " + e.getMessage());
        }
        return userOrderList;
    }

    public static void createNewUser(User user, ConnectionPool connectionPool) throws DatabaseException {
        String sql = "INSERT INTO users(user_name, user_password, role, user_email, user_tlf, is_paid_status, user_address) VALUES (?, ?, ?, ?, ?, ?, ?) ";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)
        ) {
            ps.setString(1, user.getUserName());
            ps.setString(2, user.getUserPassword());
            ps.setString(3, user.getRole());
            ps.setString(4, user.getUserEmail());
            ps.setInt(5, user.getUserTlf());
            ps.setBoolean(6, user.isPaidStatus());
            ps.setString(7, user.getAddress());
            ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Failed could not insert new user in db: " + e.getMessage());
        }
    }

    public static List<User> adminChangeUserOrderData(ConnectionPool connectionPool, int userId) throws DatabaseException {
        List<User> userOrderData = new ArrayList<>();

        String sql = "select u.user_name, " +
                "ol.cost_price, " +
                "u.user_email, " +
                "u.user_tlf, " +
                "u.user_address " +
                "from order_line ol " +
                "join users u on ol.user_id = u.user_id " +
                "where u.role= 'postgres' " +
                "and ol.order_id is not null " +
                "and u.user_id = ?";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)
        ) {

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String userName = rs.getString("user_name");
                BigDecimal costPrice = rs.getBigDecimal("cost_price");
                String userEmail = rs.getString("user_email");
                int userTlf = rs.getInt("user_tlf");
                String address = rs.getString("user_address");

                User changeOrder = new User(userName, costPrice, userEmail, userTlf, address);
                userOrderData.add(changeOrder);

            }
        } catch (SQLException e) {
            throw new DatabaseException("Failed trying to get the the details of the specific user: " + userId + e.getMessage());
        }
        return userOrderData;
    }

    public static List<UserDTO> getUserNameAndOrderIdByUserId(ConnectionPool connectionPool, int userId) throws DatabaseException {
        List<UserDTO> orderIdPlusUserName = new ArrayList<>();
        String sql = "select o.order_id, u.user_name " +
                "from orders o " +
                "join users u on o.user_id = u.user_id " +
                "where u.role='postgres' " +
                "and o.order_id is not null " +
                "and u.user_id= ?";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)
        ) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int orderId = rs.getInt("order_id");
                String userName = rs.getString("user_name");
                UserDTO orderIdAndUserName = new UserDTO(orderId, userName);
                orderIdPlusUserName.add(orderIdAndUserName);
            }
        } catch (SQLException e) {
            throw new DatabaseException("Failed trying to get orderId and userName" + e.getMessage());
        }
        return orderIdPlusUserName;
    }

    public static void updateUserName(ConnectionPool connectionPool, String userName, int userId) throws DatabaseException {
        String sql = "update users set user_name = ? " +
                "where user_id = ? and role='postgres'";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)
        ) {
            ps.setString(1, userName);
            ps.setInt(2, userId);

            int rowsAffected = ps.executeUpdate();

            if (rowsAffected != 1) {
                throw new DatabaseException("Failed while trying to update userName");
            }


        } catch (SQLException e) {
            throw new DatabaseException("Failed while trying to update userName." + e.getMessage());
        }
    }

    public static void updateCostPrice(ConnectionPool connectionPool, BigDecimal costPrice, int userId) throws DatabaseException {
        String sql = "update order_line set cost_price = ? " +
                "where user_id = ? and role='postgres'";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)
        ) {
            ps.setBigDecimal(1, costPrice);
            ps.setInt(2, userId);

            int rowsAffected = ps.executeUpdate();

            if (rowsAffected != 1) {
                throw new DatabaseException("Failed while trying to update costPrice");
            }


        } catch (SQLException e) {
            throw new DatabaseException("Failed while trying to update costPrice" + e.getMessage());
        }
    }

    public static void updateUserEmail(ConnectionPool connectionPool, String userEmail, int userId) throws DatabaseException{
        String sql = "update users set user_email = ? " +
                "where user_id = ? and role='postgres'";

        try(Connection connection = connectionPool.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql)
        ){
            ps.setString(1, userEmail);
            ps.setInt(2, userId);

            int rowsAffected = ps.executeUpdate();

            if(rowsAffected != 1){
                throw new DatabaseException("Failed while trying to update userEmail");
            }


        }catch(SQLException e){
            throw new DatabaseException("Failed while trying to update userEmail" + e.getMessage());
        }
    }

    public static void updateUserTlf(ConnectionPool connectionPool, int userTlf , int userId) throws DatabaseException{
        String sql = "update users set user_tlf = ? " +
                "where user_id = ? and role='postgres'";

        try(Connection connection = connectionPool.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql)
        ){
            ps.setInt(1, userTlf);
            ps.setInt(2, userId);

            int rowsAffected = ps.executeUpdate();

            if(rowsAffected != 1){
                throw new DatabaseException("Failed while trying to update userTlf");
            }


        }catch(SQLException e){
            throw new DatabaseException("Failed while trying to update userTlf" + e.getMessage());
        }
    }

    public static void updateUserAddress(ConnectionPool connectionPool, String userAddress , int userId) throws DatabaseException{
        String sql = "update users set user_address = ? " +
                "where user_id = ? and role='postgres'";

        try(Connection connection = connectionPool.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql)
        ){
            ps.setString(1, userAddress);
            ps.setInt(2, userId);

            int rowsAffected = ps.executeUpdate();

            if(rowsAffected != 1){
                throw new DatabaseException("Failed while trying to update userTlf");
            }


        }catch(SQLException e){
            throw new DatabaseException("Failed while trying to update userTlf" + e.getMessage());
        }
    }
}
