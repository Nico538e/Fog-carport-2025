package app.entities;

public class OrderLine {
    private int orderLineId;
    private int orderId;
    private int userId;
    private int itemId;
    private int costPrice;
    private ItemVariant itemVariant;
    private Order order;
    private String description;
    private int itemPackageAmount;

    public OrderLine(int orderLineId, int orderId, int userId, int itemId, int costPrice, ItemVariant itemVariant, Order order, String description, int itemPackageAmount) {
        this.orderLineId = orderLineId;
        this.orderId = orderId;
        this.userId = userId;
        this.itemId = itemId;
        this.costPrice = costPrice;
        this.itemVariant = itemVariant;
        this.order = order;
        this.description = description;
        this.itemPackageAmount = itemPackageAmount;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    @Override
    public String toString() {
        return "OrderLine{" +
                "orderLineId=" + orderLineId +
                ", orderId=" + orderId +
                ", userId=" + userId +
                ", itemId=" + itemId +
                ", costPrice=" + costPrice +
                ", itemVariant=" + itemVariant +
                ", orders=" + order +
                ", description='" + description + '\'' +
                '}';
    }
}
