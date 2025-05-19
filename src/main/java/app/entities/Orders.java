package app.entities;

public class Orders {
    private int orderId;
    private int userId;
    private User user;
    private int length;
    private int width;

    public Orders(int orderId, int userId, User user, int length, int width) {
        this.orderId = orderId;
        this.userId = userId;
        this.user = user;
        this.length = length;
        this.width = width;
    }

    public Orders(int userId, int length, int width) {
        this.userId = userId;
        this.length = length;
        this.width = width;
    }

    public Orders(int orderId, int userId, User user) {
        this.orderId = orderId;
        this.userId = userId;
        this.user = user;
    }

    public Orders(int orderId, int userId) {
        this.orderId = orderId;
        this.userId = userId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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

    @Override
    public String toString() {
        return "Orders{" +
                "orderId=" + orderId +
                ", userId=" + userId +
                ", user=" + user +
                '}';
    }
}
