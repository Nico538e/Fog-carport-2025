package app.DTO;

import app.entities.User;

public class UserOrderDTO {
    private int orderId;
    private int userId;
    private User user;
    private int length;
    private int width;

    private String userName;
    private String userPassword;
    private String role;
    private String userEmail;
    private int userTlf;
    private String address;

    public UserOrderDTO(int orderId, int userId, User user, int length, int width, int userId1, String userName, String userPassword, String role, String userEmail, int userTlf, String address) {
        this.orderId = orderId;
        this.userId = userId;
        this.user = user;
        this.length = length;
        this.width = width;
        this.userName = userName;
        this.userPassword = userPassword;
        this.role = role;
        this.userEmail = userEmail;
        this.userTlf = userTlf;
        this.address = address;
    }

    //adminPage1
    public UserOrderDTO(int userId, int orderId, String userName, String userEmail, int userTlf, String address, int length, int width) {
        this.userId = userId;
        this.orderId = orderId;
        this.userName = userName;
        this.userEmail = userEmail;
        this.userTlf = userTlf;
        this.address = address;
        this.length = length;
        this.width = width;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }
}
