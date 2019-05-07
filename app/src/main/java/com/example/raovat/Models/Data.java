package com.example.raovat.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("Id")
    @Expose
    private String id;



    public String getId() {
        return id;
    }


}