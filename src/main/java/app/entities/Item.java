package app.entities;

public class Item {
    private int itemId;
    private String itemName;
    private int itemHeight;
    private int itemWidth;
    private String itemType;
    private String itemPackageType;
    private int itemCostPrice;

    public Item(int itemId, String itemName, int itemHeight, int itemWidth, int itemTypeId, String itemPackageType, int itemCostPrice) {
        this.itemCostPrice = itemCostPrice;
        this.itemPackageType = itemPackageType;
        this.itemWidth = itemWidth;
        this.itemHeight = itemHeight;
        this.itemName = itemName;
        this.itemId = itemId;
    }

    public Item(int itemId, String itemName, String itemType, String itemPackageType, int itemCostPrice) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.itemType = itemType;
        this.itemPackageType = itemPackageType;
        this.itemCostPrice = itemCostPrice;

    }

    public int getItemId() {
        return itemId;
    }

    public int getItemCostPrice() {
        return itemCostPrice;
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
