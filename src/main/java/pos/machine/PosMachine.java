package pos.machine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.lang.String.format;

public class PosMachine {
    public String printReceipt(List<String> barcodes) {
        List<ItemInfo> scannedItemsWithInfo = mapBarcodeItems(barcodes);
        Receipt receipt = computeReceipt(scannedItemsWithInfo);
        return displayReceipt(receipt);
    }

    private String displayReceipt(Receipt receipt) {
        String subItemsDetails = concatSubItems(receipt.getItemsWithSubTotal());
        String receiptDisplay = "***<store earning no money>Receipt***\n" + subItemsDetails + "----------------------\n"
                + ("Total: " + receipt.getTotalPrice() + " (yuan)\n"
                + "**********************");
        return receiptDisplay;
    }

    private String concatSubItems(List<ScannedItem> itemsWithSubTotal) {
        return itemsWithSubTotal.stream()
                .map(items -> format("Name: %s, Quantity: %d, Unit price: %d (yuan), Subtotal: %d (yuan)\n",
                        items.getName(), items.getQuantity(), items.getPrice(), items.getSubTotal()))
                .collect(Collectors.joining(""));
    }

    private Receipt computeReceipt(List<ItemInfo> scannedItemsWithInfo) {
        List<ScannedItem> itemsWithInfoSubtotal = buildItemWithSubtotal(scannedItemsWithInfo);
        int totalPrice = computeTotal(itemsWithInfoSubtotal);
        Receipt receipt = new Receipt(itemsWithInfoSubtotal, totalPrice);
        return receipt;
    }

    private int computeTotal(List<ScannedItem> scannedItems) {
        return scannedItems.stream().map(ScannedItem::getSubTotal).reduce(0, Integer::sum);
    }

    private List<ScannedItem> buildItemWithSubtotal(List<ItemInfo> scannedItemsWithInfo) {
        List<ScannedItem> itemsWithInfo = mapOrderInfo(scannedItemsWithInfo);
        List<ScannedItem> itemsWithInfoSubtotal = computeSubtotal(itemsWithInfo);
        return itemsWithInfoSubtotal;
    }

    private List<ScannedItem> mapOrderInfo(List<ItemInfo> scannedItemsWithInfo) {
        return scannedItemsWithInfo.stream()
                .map(itemInfo -> new ScannedItem(itemInfo.getBarcode(), itemInfo.getName(), 0, itemInfo.getPrice(), 0))
                .collect(Collectors.toList());
    }

    private List<ScannedItem> computeSubtotal(List<ScannedItem> scannedItems) {
        Map<ScannedItem, Integer> scannedItemQtyMap = new HashMap<>();
        for (ScannedItem scannedItem : scannedItems) {
            Integer previousCount = scannedItemQtyMap.getOrDefault(scannedItem, 0);
            scannedItemQtyMap.put(scannedItem, previousCount + 1);
        }

        List<ScannedItem> distinctScannedItem = new ArrayList<>(scannedItemQtyMap.keySet());
        for (ScannedItem scannedItem : distinctScannedItem) {
            int quantity = scannedItemQtyMap.get(scannedItem);
            int subtotal = quantity * scannedItem.getPrice();
            scannedItem.setQuantity(quantity);
            scannedItem.setSubTotal(subtotal);
        }

        return distinctScannedItem;
    }

    private List<ItemInfo> mapBarcodeItems(List<String> barcodes) {
        return barcodes.stream()
                .map(this::findItemInfoByBarcode)
                .collect(Collectors.toList());
    }

    private ItemInfo findItemInfoByBarcode(String barcode) {
        List<ItemInfo> itemInfos = ItemDataLoader.loadAllItemInfos();
        return itemInfos.stream().filter(itemInfo -> itemInfo.getBarcode().equals(barcode)).findFirst().get();
    }

}
