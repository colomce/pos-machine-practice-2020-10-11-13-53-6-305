package pos.machine;

import java.util.List;

public class Receipt {

    private List<ScannedItem> itemsWithSubTotal;
    private int totalPrice;

    public Receipt(List<ScannedItem> itemsWithSubTotal, int totalPrice) {
        this.itemsWithSubTotal = itemsWithSubTotal;
        this.totalPrice = totalPrice;
    }

    public List<ScannedItem> getItemsWithSubTotal() {
        return itemsWithSubTotal;
    }

    public void setItemsWithSubTotal(List<ScannedItem> itemsWithSubTotal) {
        this.itemsWithSubTotal = itemsWithSubTotal;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }
}
