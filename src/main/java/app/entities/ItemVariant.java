package app.entities;

public class ItemVariant {
    private int variantId;
    private Item itemId;
    private int variantLength;


    public ItemVariant(int variantId, Item itemId, int variantLength) {
        this.variantId = variantId;
        this.itemId = itemId;
        this.variantLength = variantLength;
    }

    public int getVariantId() {
        return variantId;
    }

    public void setVariantId(int variantId) {
        this.variantId = variantId;
    }

    public Item getItemId() {
        return itemId;
    }

    public void setItemId(Item itemId) {
        this.itemId = itemId;
    }

    public int getVariantLength() {
        return variantLength;
    }

    public void setVariantLength(int variantLength) {
        this.variantLength = variantLength;
    }
}
