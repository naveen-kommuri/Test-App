package com.test.testapplication.model;

/**
 * Created by Naveen on 8/19/2017.
 */

public class User {
    String name, emailId, contactNo, password, pan, gstin, gstinRegDate, regType, pincode;

    public User(String emailId, String password) {
        this.emailId = emailId;
        this.password = password;
    }

    public User(String name, String emailId, String contactNo, String password, String pan, String gstin, String gstinRegDate, String regType, String pincode) {
        this.name = name;
        this.emailId = emailId;
        this.contactNo = contactNo;
        this.password = password;
        this.pan = pan;
        this.gstin = gstin;
        this.gstinRegDate = gstinRegDate;
        this.regType = regType;
        this.pincode = pincode;
    }

    public String getName() {
        return name;
    }

    public User setName(String name) {
        this.name = name;
        return this;
    }

    public String getEmailId() {
        return emailId;
    }

    public User setEmailId(String emailId) {
        this.emailId = emailId;
        return this;
    }

    public String getContactNo() {
        return contactNo;
    }

    public User setContactNo(String contactNo) {
        this.contactNo = contactNo;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getPan() {
        return pan;
    }

    public User setPan(String pan) {
        this.pan = pan;
        return this;
    }

    public String getGstin() {
        return gstin;
    }

    public User setGstin(String gstin) {
        this.gstin = gstin;
        return this;
    }

    public String getGstinRegDate() {
        return gstinRegDate;
    }

    public User setGstinRegDate(String gstinRegDate) {
        this.gstinRegDate = gstinRegDate;
        return this;
    }

    public String getRegType() {
        return regType;
    }

    public User setRegType(String regType) {
        this.regType = regType;
        return this;
    }

    public String getPincode() {
        return pincode;
    }

    public User setPincode(String pincode) {
        this.pincode = pincode;
        return this;
    }
}
