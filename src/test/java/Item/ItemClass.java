package Item;

import lombok.Data;

@Data
public class ItemClass {
    private String item_Name;
    private int item_Price_for_quantity;
    private String item_Quantity_unit;

    public String getItem_Name() {
        return item_Name;
    }

    public void setItem_Name(String item_Name) {
        this.item_Name = item_Name;
    }

    public int getItem_Price_for_quantity() {
        return item_Price_for_quantity;
    }

    public void setItem_Price_for_quantity(int item_Price_for_quantity) {
        this.item_Price_for_quantity = item_Price_for_quantity;
    }

    public String getItem_Quantity_unit() {
        return item_Quantity_unit;
    }

    public void setItem_Quantity_unit(String item_Quantity_unit) {
        this.item_Quantity_unit = item_Quantity_unit;
    }
}