package app.entities;

import java.math.BigDecimal;

public class User {

    private int userId;
    private String userName;
    private String userPassword;
    private String role;
    private String userEmail;
    private int userTlf;
    private boolean isPaidStatus;
    private String address;
    private int orderId;
    private BigDecimal costPrice;

    public User(int userId, String userName, String userPassword, String role, String userEmail, int userTlf, boolean isPaidStatus, String address) {
        this.userId = userId;
        this.userName = userName;
        this.userPassword = userPassword;
        this.role = role;
        this.userEmail = userEmail;
        this.userTlf = userTlf;
        this.isPaidStatus = isPaidStatus;
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

    public User(int orderId, String userName, String userEmail, int userTlf, String address, boolean isPaidStatus, BigDecimal costPrice){
        this.orderId = orderId;
        this.userName = userName;
        this.userEmail = userEmail;
        this.userTlf = userTlf;
        this.address = address;
        this.isPaidStatus = isPaidStatus;
        this.costPrice = costPrice;
    }

    public User(String userName, String userPassword, String role, String userEmail, int userTlf, boolean isPaidStatus, String address){
        this.userName = userName;
        this.userPassword = userPassword;
        this.role = role;
        this.userEmail = userEmail;
        this.userTlf = userTlf;
        this.isPaidStatus = isPaidStatus;
        this.address = address;
    }

    public boolean isPaidStatus() {
        return isPaidStatus;
    }

    public void setPaidStatus(boolean paidStatus) {
        isPaidStatus = paidStatus;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public BigDecimal getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(BigDecimal costPrice) {
        this.costPrice = costPrice;
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

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", userPassword='" + userPassword + '\'' +
                ", role='" + role + '\'' +
                ", userEmail='" + userEmail + '\'' +
                ", userTlf=" + userTlf +
                ", isPaidStatus=" + isPaidStatus +
                ", address='" + address + '\'' +
                ", orderId=" + orderId +
                ", costPrice=" + costPrice +
                '}';
    }
}
