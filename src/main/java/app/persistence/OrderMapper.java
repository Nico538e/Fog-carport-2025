
package app.persistence;

import app.entities.Orders;
import app.exceptions.DatabaseException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderMapper {

    public static Orders getOrderById(int orderId, ConnectionPool connectionPool) throws DatabaseException {
        String sql = "SELECT * FROM orders WHERE order_id = ?";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, orderId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int userId = rs.getInt("user_id");
                int length = rs.getInt("carport_length");
                int width = rs.getInt("carport_width");

                return new Orders(orderId, userId, width, length);  // Matcher din Orders constructor
            } else {
                return null; // Hvis ordren ikke findes
            }

        } catch (SQLException e) {
            throw new DatabaseException("Fejl under hentning af ordre med ID " + orderId, e);
        }
    }

    public static Orders getLatestPaidOrderByUserId(int userId, ConnectionPool connectionPool) throws DatabaseException {
        String sql = "SELECT * FROM orders WHERE user_id = ? AND is_paid_status = true";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int orderId = rs.getInt("order_id");
                int length = rs.getInt("carport_length");
                int width = rs.getInt("carport_width");

                return new Orders(orderId, userId, width, length);
            } else {
                return null; // Brugeren har ingen betalte ordrer
            }

        } catch (SQLException e) {
            throw new DatabaseException("Fejl ved hentning af betalt ordre", e);
        }
    }
}