package com.example.raovat.Models;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Categorychild1 {

    @SerializedName("Posts")
    @Expose
    private List<String> posts = null;
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("CategoryChildName")
    @Expose
    private String categoryChildName;
    @SerializedName("CategoryParent")
    @Expose
    private String categoryParent;

    public List<String> getPosts() {
        return posts;
    }

    public void setPosts(List<String> posts) {
        this.posts = posts;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategoryChildName() {
        return categoryChildName;
    }

    public void setCategoryChildName(String categoryChildName) {
        this.categoryChildName = categoryChildName;
    }

    public String getCategoryParent() {
        return categoryParent;
    }

    public void setCategoryParent(String categoryParent) {
        this.categoryParent = categoryParent;
    }

}