package app.persistence;


import app.DTO.UserDTO;
import app.entities.User;
import app.exceptions.DatabaseException;

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
                String address = rs.getString("user_address");
                return new User(userId, userName, userPassword, roles, userEmail, userTlf, address);
            } else {
                throw new DatabaseException("Fejl i login. Prøv igen");
            }
        } catch (SQLException e) {
            throw new DatabaseException("DB fejl", e.getMessage());
        }
    }

    public static List<User> getUserNamesAndUserId(ConnectionPool connectionPool, String role) throws DatabaseException {
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

    public static List<User> getAllUsers(ConnectionPool connectionPool) throws DatabaseException {
        List<User> userNameList = new ArrayList<>();
        String sql = "SELECT user_id, user_name, user_email, user_tlf, user_address  FROM users where role = 'postgres'";


        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {


            while (rs.next()) {
                int userId = rs.getInt("user_id");
                String userName = rs.getString("user_name");
                String userEmail = rs.getString("user_email");
                int userTlf = rs.getInt("user_tlf");
                String address = rs.getString("user_address");


                User user = new User(userId, userName, userEmail, userTlf, address);
                user.addOrders(OrderMapper.getOrdersByUserId(connectionPool, userId));
                userNameList.add(user);
            }

        } catch (SQLException e) {
            throw new DatabaseException("Failed trying to get all userNames", e);
        }
        return userNameList;
    }

    public static User getUserByEmail(ConnectionPool connectionPool, String email) throws DatabaseException {
        String SQL = "SELECT * FROM users WHERE user_email = ?";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(SQL)
        ) {
            ps.setString(1, email);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int userId = rs.getInt("user_id");
                String userName = rs.getString("user_name");
                String password = rs.getString("user_password");
                String role = rs.getString("role");
                String userEmail = rs.getString("user_email");
                int tlf = rs.getInt("user_tlf");
                String address = rs.getString("user_address");

                return new User(userId, userName, password, role, userEmail, tlf, address);
            } else {
                throw new DatabaseException("Failed trying to find user email");
            }
        } catch (SQLException e) {
            throw new DatabaseException("Failed trying to get user email", e);
        }

    }

    public static void createNewUser(User user, ConnectionPool connectionPool) throws DatabaseException {
        String sql = "INSERT INTO users(user_name, user_password, role, user_email, user_tlf, user_address) VALUES (?, ?, ?, ?, ?, ?) ";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)
        ) {
            ps.setString(1, user.getUserName());
            ps.setString(2, user.getUserPassword());
            ps.setString(3, user.getRole());
            ps.setString(4, user.getUserEmail());
            ps.setInt(5, user.getUserTlf());
            ps.setString(6, user.getAddress());
            ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Failed could not insert new user in db: " + e.getMessage());
        }
    }

    public static User adminGetUserDataByUserid(ConnectionPool connectionPool, int userId) throws DatabaseException {
        String sql = "select user_name, " +
                "user_email, " +
                "user_tlf, " +
                "user_address " +
                "from users " +
                "where role= 'postgres' " +
                "and user_id = ?";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)
        ) {

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String userName = rs.getString("user_name");
                String userEmail = rs.getString("user_email");
                int userTlf = rs.getInt("user_tlf");
                String address = rs.getString("user_address");

                return new User(userId, userName, userEmail, userTlf, address);
            } else {
                throw new DatabaseException("The user was not found: " + userId);
            }
        } catch (SQLException e) {
            throw new DatabaseException("Failed trying to get the the details of the specific user from the database: " + userId + e.getMessage());
        }
    }

    // rediger eller slet
    public static UserDTO getUserNameAndOrderIdByUserId(ConnectionPool connectionPool, int userId) throws DatabaseException {
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

            if (rs.next()) {
                int orderId = rs.getInt("order_id");
                String userName = rs.getString("user_name");
                return new UserDTO(orderId, userName);
            } else {
                throw new DatabaseException("The userName and orderId was not found");
            }
        } catch (SQLException e) {
            throw new DatabaseException("Failed trying to get orderId and userName" + e.getMessage());
        }
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

    public static void updateUserEmail(ConnectionPool connectionPool, String userEmail, int userId) throws DatabaseException {
        String sql = "update users set user_email = ? " +
                "where user_id = ? and role='postgres'";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)
        ) {
            ps.setString(1, userEmail);
            ps.setInt(2, userId);

            int rowsAffected = ps.executeUpdate();

            if (rowsAffected != 1) {
                throw new DatabaseException("Failed while trying to update userEmail");
            }


        } catch (SQLException e) {
            throw new DatabaseException("Failed while trying to update userEmail" + e.getMessage());
        }
    }

    public static void updateUserTlf(ConnectionPool connectionPool, int userTlf, int userId) throws DatabaseException {
        String sql = "update users set user_tlf = ? " +
                "where user_id = ? and role='postgres'";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)
        ) {
            ps.setInt(1, userTlf);
            ps.setInt(2, userId);

            int rowsAffected = ps.executeUpdate();

            if (rowsAffected != 1) {
                throw new DatabaseException("Failed while trying to update userTlf");
            }


        } catch (SQLException e) {
            throw new DatabaseException("Failed while trying to update userTlf" + e.getMessage());
        }
    }

    public static void updateUserAddress(ConnectionPool connectionPool, String userAddress, int userId) throws DatabaseException {
        String sql = "update users set user_address = ? " +
                "where user_id = ? and role='postgres'";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)
        ) {
            ps.setString(1, userAddress);
            ps.setInt(2, userId);

            int rowsAffected = ps.executeUpdate();

            if (rowsAffected != 1) {
                throw new DatabaseException("Failed while trying to update userTlf");
            }


        } catch (SQLException e) {
            throw new DatabaseException("Failed while trying to update userTlf" + e.getMessage());
        }
    }
}
