package pojos;

import lombok.Data;

@Data
public class Item {
    private String itemName;
    private int itemPrice_for_quantity;
    private String itemQuantity_unit;

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getItemPrice_for_quantity() {
        return itemPrice_for_quantity;
    }

    public void setItemPrice_for_quantity(int itemPrice_for_quantity) {
        this.itemPrice_for_quantity = itemPrice_for_quantity;
    }

    public String getItemQuantity_unit() {
        return itemQuantity_unit;
    }

    public void setItemQuantity_unit(String itemQuantity_unit) {
        this.itemQuantity_unit = itemQuantity_unit;
    }
}