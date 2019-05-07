package com.example.raovat.Models;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Categoryparen {

    @SerializedName("CategoryChilds")
    @Expose
    private List<CategoryChild> categoryChilds = null;

    @SerializedName("CategoryParentName")
    @Expose
    private String categoryParentName;
    @SerializedName("CategoryParentUrl")
    @Expose
    private String categoryParentUrl;


    public List<CategoryChild> getCategoryChilds() {
        return categoryChilds;
    }


    public String getCategoryParentName() {
        return categoryParentName;
    }


}