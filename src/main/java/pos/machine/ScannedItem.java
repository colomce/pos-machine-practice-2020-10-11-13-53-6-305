package pos.machine;

import java.util.Objects;

public class ScannedItem {
    private String barcode;
    private String name;
    private int quantity;
    private int price;
    private int subTotal;

    public ScannedItem(String barcode, String name, int quantity, int price, int subTotal) {
        this.barcode = barcode;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.subTotal = subTotal;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(int subTotal) {
        this.subTotal = subTotal;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ScannedItem that = (ScannedItem) o;
        return Objects.equals(barcode, that.barcode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(barcode);
    }
}
