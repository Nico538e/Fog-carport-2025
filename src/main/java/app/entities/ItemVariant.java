package app.entities;

public class ItemVariant {
    private int variantId;
    private Item item;
    private int variantLength;


    public ItemVariant(int variantId, Item item, int variantLength) {
        this.variantId = variantId;
        this.item = item;
        this.variantLength = variantLength;
    }

    public Item getItem() {
        return item;
    }

    @Override
    public String toString() {
        return "ItemVariant{" +
                "variantId=" + variantId +
                ", item=" + item +
                ", variantLength=" + variantLength +
                '}';
    }
}
