package app.entities;

import java.math.BigDecimal;

public class User {

    private int userId;
    private String userName;
    private String userPassword;
    private String role;
    private String userEmail;
    private int userTlf;
    private String address;
    private int orderId;
    private BigDecimal costPrice;

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
    public User(int userId, int orderId, String userName, String userEmail, int userTlf, String address, BigDecimal costPrice){
        this.userId = userId;
        this.orderId = orderId;
        this.userName = userName;
        this.userEmail = userEmail;
        this.userTlf = userTlf;
        this.address = address;
        this.costPrice = costPrice;
    }

    public User(String userName, String userPassword, String role, String userEmail, int userTlf, String address){
        this.userName = userName;
        this.userPassword = userPassword;
        this.role = role;
        this.userEmail = userEmail;
        this.userTlf = userTlf;
        this.address = address;
    }


    //One specific user to change they are set to be in the input fields on adminPage2
    public User(String userName, BigDecimal costPrice, String userEmail, int userTlf, String address){
        this.userName = userName;
        this.costPrice = costPrice;
        this.userEmail = userEmail;
        this.userTlf = userTlf;
        this.address = address;
    }


    public User(String userName, int orderId) {
        this.orderId = orderId;
        this.userName = userName;
    }

    public int getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public String getRole() {
        return role;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public int getUserTlf() {
        return userTlf;
    }


    public String getAddress() {
        return address;
    }

    public int getOrderId() {
        return orderId;
    }

    public BigDecimal getCostPrice() {
        return costPrice;
    }


    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public void setUserTlf(int userTlf) {
        this.userTlf = userTlf;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public void setCostPrice(BigDecimal costPrice) {
        this.costPrice = costPrice;
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
                ", orderId=" + orderId +
                ", costPrice=" + costPrice +
                '}';
    }
}
