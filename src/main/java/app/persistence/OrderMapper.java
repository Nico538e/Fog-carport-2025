package app.persistence;

import app.entities.Order;
import app.exceptions.DatabaseException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderMapper {
    public static List<Order> getOrdersByUserId(ConnectionPool connectionPool, int userId) throws DatabaseException {
        List<Order> orderList = new ArrayList<>();

        String sql = "SELECT * FROM orders Where user_id = ?";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)
        ) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int orderId = rs.getInt("order_id");
                int length = rs.getInt("carport_length");
                int width = rs.getInt("carport_width");
                Order order = new Order(orderId, userId, length, width);
                orderList.add(order);
            }

        } catch (SQLException e) {
            throw new DatabaseException("Failed could not get the order from the specific user: " + userId, e);
        }
        return orderList;
    }

    public static void createOrders(ConnectionPool connectionPool, int userId, int length, int width) throws DatabaseException {
        String sql = "INSERT INTO orders(user_id, carport_length, carport_width) VALUES (?,?,?)";
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)
        ) {
            ps.setInt(1, userId);
            ps.setInt(2, length);
            ps.setInt(3, width);

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new DatabaseException("Failed to insert order into database", e);
        }
    }

    public static Order getOrderById(int orderId, ConnectionPool connectionPool) throws DatabaseException {
        String sql = "SELECT * FROM orders WHERE order_id = ?";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, orderId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int userId = rs.getInt("user_id");
                int length = rs.getInt("carport_length");
                int width = rs.getInt("carport_width");

                return new Order(orderId, userId, width, length);  // Matcher din Orders constructor
            } else {
                return null; // Hvis ordren ikke findes
            }

        } catch (SQLException e) {
            throw new DatabaseException("Fejl under hentning af ordre med ID " + orderId, e);
        }
    }

    public static Order getOrderByUserId(int userId, ConnectionPool connectionPool) throws DatabaseException {
        String sql = "SELECT * FROM orders WHERE user_id = ?";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int orderId = rs.getInt("order_id");
                int length = rs.getInt("carport_length");
                int width = rs.getInt("carport_width");
                boolean isPaid = rs.getBoolean("is_paid");

                return new Order(orderId, userId, width, length, isPaid);  // Matcher din Orders constructor
            } else {
                return null; // Hvis ordren ikke findes
            }

        } catch (SQLException e) {
            throw new DatabaseException("Fejl under hentning af ordre med ID " + userId, e);
        }
    }
}
