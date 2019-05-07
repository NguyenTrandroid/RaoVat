package com.example.raovat.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CategoryChild {


    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("CategoryChildName")
    @Expose
    private String categoryChildName;
    @SerializedName("CategoryParent")
    @Expose
    private String categoryParent;

    public String getId() {
        return id;
    }

    public String getCategoryChildName() {
        return categoryChildName;
    }

}