public class Protector {
    //step 2: changing
    private String invoiceNumber, stockCode, description;
    private double unitPrice, totalPrice;
    private int quantity, numberOfItems;
    public Protector(String invoiceNumber, String quantity_str, String stockCode, String description,
                     String unitPrice_str) {
        this.description = description;
        this.invoiceNumber = invoiceNumber;
        this.quantity = Integer.parseInt(quantity_str);
        this.stockCode = stockCode;
        this.unitPrice = Double.parseDouble(unitPrice_str);
    }

    /*
    //specific information

    public void setNumberOfItems(String numberOfItems_str) {
        this.numberOfItems = Integer.parseInt(numberOfItems_str);
    }
    public int getNumberOfItems() {
        return numberOfItems;
    }

    public void setTotalPrice(String totalPrice_str) {
        this.totalPrice = Double.parseDouble(totalPrice_str);
    }
    public double getTotalPrice() {
        return totalPrice;
    }
    */
    //general informations

    public int getQuantity() {
        return quantity;
    }

    public String getStockCode() {
        return stockCode;
    }

    public String getDescription() {
        return description;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public double getUnitPrice() {
        return unitPrice;
    }
}
