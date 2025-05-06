package app.entities;

public class Item {
    private int itemId;
    private String itemName;
    private int itemHeight;
    private int itemWidth;
    private String itemType;
    private String itemPackageType;
    private int itemCostPrice;

    public Item(int itemCostPrice, String itemPackageType, String itemType, int itemWidth, int itemHeight, String itemName, int itemId) {
        this.itemCostPrice = itemCostPrice;
        this.itemPackageType = itemPackageType;
        this.itemType = itemType;
        this.itemWidth = itemWidth;
        this.itemHeight = itemHeight;
        this.itemName = itemName;
        this.itemId = itemId;
    }

    public Item(int itemId, String itemName, int itemHeight, int itemWidth, int itemTypeId, String itemPackageType, int itemCostPrice) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.itemHeight = itemHeight;
        this.itemWidth = itemWidth;
        this.itemPackageType = itemPackageType;
        this.itemCostPrice = itemCostPrice;}

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getItemHeight() {
        return itemHeight;
    }

    public void setItemHeight(int itemHeight) {
        this.itemHeight = itemHeight;
    }

    public int getItemWidth() {
        return itemWidth;
    }

    public void setItemWidth(int itemWidth) {
        this.itemWidth = itemWidth;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public String getItemPackageType() {
        return itemPackageType;
    }

    public void setItemPackageType(String itemPackageType) {
        this.itemPackageType = itemPackageType;
    }

    public int getItemCostPrice() {
        return itemCostPrice;
    }

    public void setItemCostPrice(int itemCostPrice) {
        this.itemCostPrice = itemCostPrice;
    }

    @Override
    public String toString() {
        return "Item{" +
                "itemId=" + itemId +
                ", itemName='" + itemName + '\'' +
                ", itemHeight=" + itemHeight +
                ", itemWidth=" + itemWidth +
                ", itemType='" + itemType + '\'' +
                ", itemPackageType='" + itemPackageType + '\'' +
                ", itemCostPrice=" + itemCostPrice +
                '}';
    }
}
