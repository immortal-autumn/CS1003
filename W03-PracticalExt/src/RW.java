import java.io.*;
import java.text.DecimalFormat;

public class RW {
    //this class moves reader and writer from main class!
    //keep the main class brief
    private String pre_InvoiceNum = "", Month = "";
    private String MaxIn, MinIn;
    private double MaxPr = 0, MinPr = 9999;
    private static DecimalFormat df = new DecimalFormat("####0.00");
    private int[] numOfInvoices;
    private double[] totalPrice;


    public void access(String args[]) throws FileNotFoundException, IOException, ArrayIndexOutOfBoundsException {
        //step2: setter and getter change & delete useless elements
//        String invoiceNumber, quantity, numberOfItems, stockCode, description, unitPrice, totalPrice;
        PrintWriter writer = null;
        BufferedReader reader = null;
        try {
            writer = new PrintWriter(args[1]);
            reader = new BufferedReader(new FileReader(args[0]));

            String title = reader.readLine();
            String line = "";
            double totalPrice = 0;
            int numberOfItem = 0;
            Protector pt;
            initialise();

            while((line = reader.readLine()) != null) {

                /*for(int i=0;i<12;i++){
                    System.out.println(numOfInvoices[i]);
                    System.out.println(this.totalPrice[i]);
                }*/
                //load csv, split with ","
                String[] fields = line.split(",");
                pt = new Protector(fields[0], fields[3], fields[1], fields[2], fields[5]);
                pt.setMonth(fields[4]);
                //caution for cancellation
                //System.out.println(pt.getMonth());


                if (pre_InvoiceNum.equals(pt.getInvoiceNumber()) || pre_InvoiceNum.equals("") ||
                        pre_InvoiceNum.equals(pt.getInvoiceNumber().substring(1))) {
                    if(!pre_InvoiceNum.contains("C")) {
                        this.numOfInvoices[pt.getMonth() - 1] = numOfInvoices[pt.getMonth() - 1] + 1;
                        this.totalPrice[pt.getMonth() - 1] = this.totalPrice[pt.getMonth() - 1] +
                                totalUnitPrice(pt.getQuantity(), pt.getUnitPrice());
                    }
                }
                else {
                    if (!pre_InvoiceNum.equals("")) {
                        writer.println("Number of items: " + numberOfItem);
                        writer.println("Total Price: " + df.format(totalPrice));
                        writer.println();
                        getMax(pre_InvoiceNum, totalPrice);
                        getMin(pre_InvoiceNum, totalPrice);
                        totalPrice = 0;
                        numberOfItem = 0;
                    }
                }
                if(!pre_InvoiceNum.contains("C")) {
                    numberOfItem = numberOfItem + pt.getQuantity();
                    totalPrice = totalPrice + totalUnitPrice(pt.getQuantity(), pt.getUnitPrice());
                }
                //print message
                writer.println("Invoice Number: " + pt.getInvoiceNumber());
                writer.println("Stock Code: " + pt.getStockCode());
                writer.println("Description: " + pt.getDescription());
                writer.println("Quantity: " + pt.getQuantity());
                writer.println("Unit Price: " + df.format(pt.getUnitPrice()));
                //to put the products with same invoice number together and to decide when to divide
                pre_InvoiveNum(pt.getInvoiceNumber());
                //compare to decide new line*                  ------------Thu 10:45 unfinishedhere <--
                //writer.println();
                //System.out.println(pre_invoiceNum);
                /*if(compare(pre_invoiceNum, pt.getInvoiceNumber()) == true){
                totalPrice = totalPrice + totalUnitPrice(pt.getQuantity(),pt.getUnitPrice());
                }
                else {    private double[] MaxPr_M, MinPr_M;

                    writer.println(totalPrice);
                    writer.println();
                    totalPrice = 0;
                }*/
            }
            writer.println("Number of items: " + numberOfItem);
            writer.println("Total Price: " + df.format(totalPrice));
            getMax(pre_InvoiceNum,totalPrice);
            getMin(pre_InvoiceNum,totalPrice);
            writer.println();
            writer.println("Minimum priced Invoice Number: " + MinIn + " with " + df.format(MinPr));
            writer.println("Maximum priced Invoice Number: " + MaxIn + " with " + df.format(MaxPr));
            //Extension divide by month:
            writer.println();
            for (int i = 0; i < 12;i++){
                if(this.totalPrice[i] != 0){
                    writer.println("Total price in " + Month(i) + " is " + df.format(this.totalPrice[i]));
                    writer.println("Total invoice number in " + Month(i) + " is " + this.numOfInvoices[i]);
                    writer.println();
                    System.out.println("Total price in " + Month(i) + " is " + df.format(this.totalPrice[i]));
                    System.out.println("Total number of invoice number in " + Month(i) + " is " + this.numOfInvoices[i]);
                    System.out.println();
                }
            }


            // pre_InvoiveNum(pt.getInvoiceNumber());

        }
        catch (FileNotFoundException e){
            System.out.println("File not found: " + e.getMessage());
        }
        catch (IOException e){
            System.out.println("IO Exception: " + e.getMessage());
        }
        catch (ArrayIndexOutOfBoundsException e){
            System.out.println("Usage: java W03Practical <input_file> <output_file>");
        }
        catch (java.lang.NumberFormatException e){
            System.out.println("Number format exception: " + e.getMessage());
        }
        finally {
            if(writer != null) writer.close();
            if(reader != null) {
                try {
                    reader.close();
                }
                catch (IOException e){
                    System.out.println("Couldn't close reader: " + e.getMessage());
                }
            }
            //store invoice number to compare

        }

    }
    public boolean compare(String invoiceNumber1, String invoiceNumber2){
        if(invoiceNumber1.equals(invoiceNumber2)){
            return true;
        }
        else {
            return false;
        }
    }
    public void pre_InvoiveNum(String InvoiceNum){
        this.pre_InvoiceNum = InvoiceNum;
    }

    public double totalUnitPrice(int quantity, double unitPrice){
        return quantity * unitPrice;
    }

    public void getMax(String invoiceNumber, double totalPrice){
        //code example
        if (totalPrice > MaxPr && totalPrice >= 0){
            this.MaxIn = invoiceNumber;
            this.MaxPr = totalPrice;
        }
    }

    public void getMin(String invoiceNumber, double totalPrice) {
        if (totalPrice < MinPr && totalPrice > 0){
            this.MinIn = invoiceNumber;
            this.MinPr = totalPrice;
        }
    }

    public String Month(int month){
        switch (month){
            case 0:{
                return "January";
            }
            case 1:{
                return "February";
            }
            case 2:{
                return "March";
            }
            case 3:{
                return "April";
            }
            case 4:{
                return "May";
            }
            case 5:{
                return "June";
            }
            case 6:{
                return "July";
            }
            case 7:{
                return "August";
            }
            case 8:{
                return "September";
            }
            case 9:{
                return "October";
            }
            case 10:{
                return "November";
            }
            case 11:{
                return "December";
            }
            default:{
                return "    ";
            }
        }
    }

    public void initialise(){
        numOfInvoices = new int[12];
        totalPrice = new double[12];
        for(int i=0;i<12;i++){
            numOfInvoices[i] = 0;
            totalPrice[i] = 0;
        }
    }

    public void writeToHtml(){

    }
}
