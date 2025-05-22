package app.entities;

public class Order {
    private int orderId;
    private int userId;
    private User user;
    private int length;
    private int width;
    private boolean isPaid;


    public Order(int orderId, int userId, int width, int length) {
        this.orderId = orderId;
        this.userId = userId;
        this.length = length;
        this.width = width;
    }

    public int getOrderId() {
        return orderId;
    }

    public int getUserId() {
        return userId;
    }

    public User getUser() {
        return user;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getLength() {
        return length;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    @Override
    public String toString() {
        return "Orders{" +
                "orderId=" + orderId +
                ", userId=" + userId +
                ", user=" + user +
                '}';
    }
}
