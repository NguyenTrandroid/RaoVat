package com.example.raovat.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data1 {

    @SerializedName("Status")
    @Expose
    private Boolean status;

    @SerializedName("_id")
    @Expose
    private String id;


    @SerializedName("Address")
    @Expose
    private String address;


    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}
