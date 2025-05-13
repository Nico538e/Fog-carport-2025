package app.entities;

import java.math.BigDecimal;

public class ShowUserOrders {
    private int orderId;
    private String userName;
    private String userEmail;
    private int userTlf;
    private String address;
    private boolean isPaidStatus;
    private BigDecimal costPrice;

    public ShowUserOrders(int orderId, String userName, String userEmail, int userTlf, String address, boolean isPaidStatus, BigDecimal costPrice){
        this.orderId = orderId;
        this.userName = userName;
        this.userEmail = userEmail;
        this.userTlf = userTlf;
        this.address = address;
        this.isPaidStatus = isPaidStatus;
        this.costPrice = costPrice;
    }

    public int getOrderId() {
        return orderId;
    }

    public String getUserName() {
        return userName;
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

    public boolean isPaidStatus() {
        return isPaidStatus;
    }

    public BigDecimal getCostPrice() {
        return costPrice;
    }

    @Override
    public String toString() {
        return "ShowUserOrders{" +
                "orderId=" + orderId +
                ", userName='" + userName + '\'' +
                ", userEmail='" + userEmail + '\'' +
                ", userTlf=" + userTlf +
                ", address='" + address + '\'' +
                ", isPaidStatus=" + isPaidStatus +
                ", costPrice=" + costPrice +
                '}';
    }
}
