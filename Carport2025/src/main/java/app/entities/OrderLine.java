package app.entities;

public class OrderLine {
private int orderLineId;
private int orderId;
private int userId;
private int itemId;
private int costPrice;

    public OrderLine(int orderLineId, int orderId, int userId, int itemId, int costPrice) {
        this.orderLineId = orderLineId;
        this.orderId = orderId;
        this.userId = userId;
        this.itemId = itemId;
        this.costPrice = costPrice;
    }

    public int getOrderLineId() {
        return orderLineId;
    }

    public void setOrderLineId(int orderLineId) {
        this.orderLineId = orderLineId;
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

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public int getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(int costPrice) {
        this.costPrice = costPrice;
    }

    @Override
    public String toString() {
        return "OrderLine{" +
                "orderLineId=" + orderLineId +
                ", orderId=" + orderId +
                ", userId=" + userId +
                ", itemId=" + itemId +
                ", costPrice=" + costPrice +
                '}';
    }
}
