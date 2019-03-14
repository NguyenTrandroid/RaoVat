package com.example.raovat.Models;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum1 {

    @SerializedName("CategoryChilds")
    @Expose
    private List<String> categoryChilds = null;
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("CategoryParentName")
    @Expose
    private String categoryParentName;
    @SerializedName("CategoryParentUrl")
    @Expose
    private String categoryParentUrl;
    @SerializedName("__v")
    @Expose
    private Integer v;

    public List<String> getCategoryChilds() {
        return categoryChilds;
    }

    public void setCategoryChilds(List<String> categoryChilds) {
        this.categoryChilds = categoryChilds;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategoryParentName() {
        return categoryParentName;
    }

    public void setCategoryParentName(String categoryParentName) {
        this.categoryParentName = categoryParentName;
    }

    public String getCategoryParentUrl() {
        return categoryParentUrl;
    }

    public void setCategoryParentUrl(String categoryParentUrl) {
        this.categoryParentUrl = categoryParentUrl;
    }

    public Integer getV() {
        return v;
    }

    public void setV(Integer v) {
        this.v = v;
    }




}