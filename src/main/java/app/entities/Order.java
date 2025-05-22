package app.entities;

import java.util.Objects;

public class Order {
    private int orderId;
    private int userId;
    private User user;
    private int length;
    private int width;
    private boolean isPaid;

    public Order(int orderId, int userId, int length, int width, boolean isPaid) {
        this.orderId = orderId;
        this.userId = userId;
        this.length = length;
        this.width = width;
        this.isPaid = isPaid;
        this.user = user;
    }

    public Order(int orderId, int userId, int width, int length) {
        this.orderId = orderId;
        this.userId = userId;
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

    public boolean isPaid() {
        return isPaid;
    }

    public void setPaid(boolean paid) {
        isPaid = paid;
    }

    @Override
    public String toString() {
        return "Orders{" +
                "orderId=" + orderId +
                ", userId=" + userId +
                ", user=" + user +
                '}';
    }

    @Override
    public final boolean equals(Object o) {
        if (!(o instanceof Order order)) return false;

        return getOrderId() == order.getOrderId() && getUserId() == order.getUserId() && getLength() == order.getLength() && getWidth() == order.getWidth() && isPaid() == order.isPaid() && getUser().equals(order.getUser());
    }

    @Override
    public int hashCode() {
        int result = getOrderId();
        result = 31 * result + getUserId();
        result = 31 * result + getUser().hashCode();
        result = 31 * result + getLength();
        result = 31 * result + getWidth();
        result = 31 * result + Boolean.hashCode(isPaid());
        return result;
    }
}
