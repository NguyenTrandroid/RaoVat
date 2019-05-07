package com.example.raovat.Models;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SearchKey {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("data")
    @Expose
    private List<Post> data = null;

    public Boolean getStatus() {
        return status;
    }


    public List<Post> getData() {
        return data;
    }


}