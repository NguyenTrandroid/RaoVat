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

    @SerializedName("dataPost")
    @Expose
    private List<Data> dataPost = null;

    public List<Data> getDataPost() {
        return dataPost;
    }

    public void setDataPost(List<Data> dataPost) {
        this.dataPost = dataPost;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public List<Datum1> getData() {
        return data;
    }

    public void setData(List<Datum1> data) {
        this.data = data;
    }

}