package com.example.raovat.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PostNew {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("data")
    @Expose
    private Data1 data;

    public Boolean getStatus() {
        return status;
    }


    public Data1 getData() {
        return data;
    }

}