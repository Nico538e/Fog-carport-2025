package app.entities;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class User {

    private int userId;
    private String userName;
    private String userPassword;
    private String role;
    private String userEmail;
    private int userTlf;
    private String address;
    private final List<Order> orders = new ArrayList<>();

    public User(int userId, String userName, String userPassword, String role, String userEmail, int userTlf, String address) {
        this.userId = userId;
        this.userName = userName;
        this.userPassword = userPassword;
        this.role = role;
        this.userEmail = userEmail;
        this.userTlf = userTlf;

        this.address = address;
    }



    public User(int userTlf, String userEmail, String role, String userPassword, String userName) {
        this.userTlf = userTlf;
        this.userEmail = userEmail;
        this.role = role;
        this.userPassword = userPassword;
        this.userName = userName;
    }

    public User(int userId, String userName) {
        this.userId = userId;
        this.userName = userName;
    }

    //Show all users with order info in a table on adminPage1
    public User(int userId, int orderId, String userName, String userEmail, int userTlf, String address, BigDecimal costPrice) {
        this.userId = userId;
        this.userName = userName;
        this.userEmail = userEmail;
        this.userTlf = userTlf;
        this.address = address;

    }

    public User(String userName, String userPassword, String role, String userEmail, int userTlf, String address) {
        this.userName = userName;
        this.userPassword = userPassword;
        this.role = role;
        this.userEmail = userEmail;
        this.userTlf = userTlf;
        this.address = address;
    }


    //One specific user to change they are set to be in the input fields on adminPage2
    public User(String userName, String userEmail, int userTlf, String address) {
        this.userName = userName;
        this.userEmail = userEmail;
        this.userTlf = userTlf;
        this.address = address;
    }


    public User(String userName, int orderId) {

        this.userName = userName;
    }

    public User(int userId, String userName, String userEmail, int userTlf, String address) {
        this.userId = userId;
        this.userName = userName;
        this.userEmail = userEmail;
        this.userTlf = userTlf;
        this.address = address;
    }


    public void addOrder(Order order) {
        this.orders.add(order);
    }

    public void addOrders(List<Order> orders) {
        this.orders.addAll(orders);
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public int getUserTlf() {
        return userTlf;
    }

    public void setUserTlf(int userTlf) {
        this.userTlf = userTlf;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", userPassword='" + userPassword + '\'' +
                ", role='" + role + '\'' +
                ", userEmail='" + userEmail + '\'' +
                ", userTlf=" + userTlf +
                ", address='" + address + '\'' +
                ", orders=" + orders +
                '}';
    }

    @Override
    public final boolean equals(Object o) {
        if (!(o instanceof User user)) return false;

        return getUserId() == user.getUserId() && getUserTlf() == user.getUserTlf() && getUserName().equals(user.getUserName()) && getUserPassword().equals(user.getUserPassword()) && getRole().equals(user.getRole()) && getUserEmail().equals(user.getUserEmail()) && getAddress().equals(user.getAddress()) && orders.equals(user.orders);
    }

    @Override
    public int hashCode() {
        int result = getUserId();
        result = 31 * result + getUserName().hashCode();
        result = 31 * result + getUserPassword().hashCode();
        result = 31 * result + getRole().hashCode();
        result = 31 * result + getUserEmail().hashCode();
        result = 31 * result + getUserTlf();
        result = 31 * result + getAddress().hashCode();
        result = 31 * result + orders.hashCode();
        return result;
    }
}
