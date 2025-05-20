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

    public int getVariantId() {
        return variantId;
    }

    public void setVariantId(int variantId) {
        this.variantId = variantId;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public int getVariantLength() {
        return variantLength;
    }

    public void setVariantLength(int variantLength) {
        this.variantLength = variantLength;
    }
}
