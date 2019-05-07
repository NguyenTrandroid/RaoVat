package com.example.raovat.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User1 {

    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("PhoneNumber")
    @Expose
    private String phoneNumber;
    @SerializedName("FullName")
    @Expose
    private String fullName;
    @SerializedName("Address")
    @Expose
    private String address;


    public String getId() {
        return id;
    }


    public String getPhoneNumber() {
        return phoneNumber;
    }


    public String getFullName() {
        return fullName;
    }


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


}