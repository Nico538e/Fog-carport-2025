package app.entities;

public class Orders {
    private int orderId;
    private int userId;
    private User user;

    public Orders(int orderId, int userId, User user) {
        this.orderId = orderId;
        this.userId = userId;
        this.user = user;
    }

    public Orders(int orderId, int userId) {
        this.orderId = orderId;
        this.userId = userId;
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

    @Override
    public String toString() {
        return "Orders{" +
                "orderId=" + orderId +
                ", userId=" + userId +
                ", user=" + user +
                '}';
    }
}
