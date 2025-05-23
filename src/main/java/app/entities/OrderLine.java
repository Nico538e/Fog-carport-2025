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

    public int getOrderLineId() {
        return orderLineId;
    }

    public void setOrderLineId(int orderLineId) {
        this.orderLineId = orderLineId;
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

    public ItemVariant getItemVariant() {
        return itemVariant;
    }

    public void setItemVariant(ItemVariant itemVariant) {
        this.itemVariant = itemVariant;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getItemPackageAmount() {
        return itemPackageAmount;
    }

    public void setItemPackageAmount(int itemPackageAmount) {
        this.itemPackageAmount = itemPackageAmount;
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
