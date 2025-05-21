
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
}