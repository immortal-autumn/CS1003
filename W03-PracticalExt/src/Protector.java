public class Protector {
    //step 2: changing
    private String invoiceNumber, stockCode, description;
    private double unitPrice, totalPrice;
    private int quantity, numberOfItems;
    private int month;
    public Protector(String invoiceNumber, String quantity_str, String stockCode, String description,
                     String unitPrice_str) {
//        if(invoiceNumber.contains("C")){
//            invoiceNumber = invoiceNumber.substring(1);
//        }
        this.description = description;
        this.invoiceNumber = invoiceNumber;
        this.quantity = Integer.parseInt(quantity_str);
        this.stockCode = stockCode;
        this.unitPrice = Double.parseDouble(unitPrice_str);
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public void setMonth(String month_str){
        this.month = Integer.parseInt(month_str.substring(3,5));
    }

    public int getMonth() {
        return month;
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
