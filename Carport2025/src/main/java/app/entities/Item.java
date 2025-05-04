package app.entities;

public class Item {
    private int itemId;
    private String itemName;
    private int itemLength;
    private int itemHeight;
    private int itemWidth;
    private String itemType;
    private String itemMaterialType;
    private int itemPackageAmount;
    private String itemPackageType;
    private int itemCostPrice;

    public Item(int itemId, String itemName, int itemLength, int itemHeight, int itemWidth, String itemType, String itemMaterialType, int itemPackageAmount, String itemPackageType, int itemCostPrice) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.itemLength = itemLength;
        this.itemHeight = itemHeight;
        this.itemWidth = itemWidth;
        this.itemType = itemType;
        this.itemMaterialType = itemMaterialType;
        this.itemPackageAmount = itemPackageAmount;
        this.itemPackageType = itemPackageType;
        this.itemCostPrice = itemCostPrice;
    }

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

    public int getItemLength() {
        return itemLength;
    }

    public void setItemLength(int itemLength) {
        this.itemLength = itemLength;
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

    public String getItemMaterialType() {
        return itemMaterialType;
    }

    public void setItemMaterialType(String itemMaterialType) {
        this.itemMaterialType = itemMaterialType;
    }

    public int getItemPackageAmount() {
        return itemPackageAmount;
    }

    public void setItemPackageAmount(int itemPackageAmount) {
        this.itemPackageAmount = itemPackageAmount;
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
                ", itemLength=" + itemLength +
                ", itemHeight=" + itemHeight +
                ", itemWidth=" + itemWidth +
                ", itemType='" + itemType + '\'' +
                ", itemMaterialType='" + itemMaterialType + '\'' +
                ", itemPackageAmount=" + itemPackageAmount +
                ", itemPackageType='" + itemPackageType + '\'' +
                ", itemCostPrice=" + itemCostPrice +
                '}';
    }
}
