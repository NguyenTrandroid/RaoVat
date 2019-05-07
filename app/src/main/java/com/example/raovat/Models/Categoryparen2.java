package com.example.raovat.Models;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Categoryparen2 {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("data")
    @Expose
    private List<Datum1> data = null;


    public Boolean getStatus() {
        return status;
    }


    public List<Datum1> getData() {
        return data;
    }


}