package app.entities;

import javax.lang.model.element.NestingKind;

public class OrderLine {
    private int orderLineId;
    private int orderId;
    private int userId;
    private int itemId;
    private int costPrice;
    private ItemVariant itemVariant;
    private Orders orders;
    private String description;


    public OrderLine(int orderLineId, int orderId, int userId, int itemId, int costPrice) {
        this.orderLineId = orderLineId;
        this.orderId = orderId;
        this.userId = userId;
        this.itemId = itemId;
        this.costPrice = costPrice;
    }

    public OrderLine(int orderLineId, Orders orders, int userId, int itemId, int costPrice, ItemVariant itemVariant) {
        this.orderLineId = orderLineId;
        this.orders = orders;
        this.userId = userId;
        this.itemId = itemId;
        this.costPrice = costPrice;
        this.itemVariant = itemVariant;
    }

    public OrderLine(int orderLineId, Orders orders, int userId, int itemId, int costPrice, ItemVariant itemVariant, String description) {
        this.orderLineId = orderLineId;
        this.orders = orders;
        this.userId = userId;
        this.itemId = itemId;
        this.costPrice = costPrice;
        this.itemVariant = itemVariant;
        this.description = description;
    }

    public ItemVariant getItemVariant() {
        return itemVariant;
    }

    public void setItemVariant(ItemVariant itemVariant) {
        this.itemVariant = itemVariant;
    }

    public Orders getOrders() {
        return orders;
    }

    public void setOrders(Orders orders) {
        this.orders = orders;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
                ", itemVariant=" + itemVariant +
                ", orders=" + orders +
                ", description='" + description + '\'' +
                '}';
    }
}
