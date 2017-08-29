package com.test.testapplication.model;

/**
 * Created by NKommuri on 8/29/2017.
 */

public class Invoice {
    String invoiceAmount, invoiceNo, invoiceGSTIn, invoiceDate, merchantName, invoiceFileLoc;

    public String getInvoiceFileLoc() {
        return invoiceFileLoc;
    }

    public void setInvoiceFileLoc(String invoiceFileLoc) {
        this.invoiceFileLoc = invoiceFileLoc;
    }

    public String getInvoiceAmount() {
        return invoiceAmount;
    }

    public void setInvoiceAmount(String invoiceAmount) {
        this.invoiceAmount = invoiceAmount;
    }

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public String getInvoiceGSTIn() {
        return invoiceGSTIn;
    }

    public void setInvoiceGSTIn(String invoiceGSTIn) {
        this.invoiceGSTIn = invoiceGSTIn;
    }

    public String getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(String invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }
}
