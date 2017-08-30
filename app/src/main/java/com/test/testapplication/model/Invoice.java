package com.test.testapplication.model;

import android.net.Uri;

import java.io.File;

/**
 * Created by NKommuri on 8/29/2017.
 */

public class Invoice {
    String invoiceAmount, invoiceNo, invoiceGSTIn, invoiceDate, merchantName, invoiceFileLoc, invoiceStatus, updatedTime;
    int invoiceId;
    File file;

    public Invoice(String invoiceAmount, String invoiceNo, String invoiceGSTIn, String invoiceDate, String merchantName, String invoiceFileLoc) {
        this.invoiceAmount = invoiceAmount;
        this.invoiceNo = invoiceNo;
        this.invoiceGSTIn = invoiceGSTIn;
        this.invoiceDate = invoiceDate;
        this.merchantName = merchantName;
        this.invoiceFileLoc = invoiceFileLoc;
    }

    public int getInvoiceId() {
        return invoiceId;
    }

    public File getFile() {
        return file;
    }

    public Invoice setInvoiceId(int invoiceId) {
        this.invoiceId = invoiceId;
        return this;
    }

    public Invoice setInvoiceStatus(String invoiceStatus) {
        this.invoiceStatus = invoiceStatus;
        return this;
    }

    public String getInvoiceStatus() {
        return invoiceStatus;
    }

    public String getUpdatedTime() {
        return updatedTime;
    }

    public Invoice setUpdatedTime(String updatedTime) {
        this.updatedTime = updatedTime;
        return this;
    }

    public String getInvoiceFileLoc() {
        return invoiceFileLoc;
    }

    public void setInvoiceFileLoc(String invoiceFileLoc) {
        this.invoiceFileLoc = invoiceFileLoc;
        this.file = new File(invoiceFileLoc);
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
