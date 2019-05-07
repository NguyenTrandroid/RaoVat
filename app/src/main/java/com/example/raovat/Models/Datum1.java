package com.example.raovat.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum1 {


    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("CategoryParentName")
    @Expose
    private String categoryParentName;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategoryParentName() {
        return categoryParentName;
    }


}