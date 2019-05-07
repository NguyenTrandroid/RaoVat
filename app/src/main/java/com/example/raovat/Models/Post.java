package com.example.raovat.Models;

import java.util.Date;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Post extends PostNew {

    @SerializedName("PostUrl")
    @Expose
    private List<String> postUrl = null;
    @SerializedName("Status")
    @Expose
    private Boolean status;

    @SerializedName("FileId")
    @Expose
    private List<String> fileId = null;


    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("PostName")
    @Expose
    private String postName;
    @SerializedName("Price")
    @Expose
    private String price;
    @SerializedName("Address")
    @Expose
    private String address;
    @SerializedName("PhoneNumber")
    @Expose
    private String phoneNumber;
    @SerializedName("Description")
    @Expose
    private String description;
    @SerializedName("UserId")
    @Expose
    private String userId;
    @SerializedName("AreaId")
    @Expose
    private String areaId;
    @SerializedName("CategoryChildId")
    @Expose
    private String categoryChildId;
    @SerializedName("PostDate")
    @Expose
    private Date postDate;


    public List<String> getPostUrl() {
        return postUrl;
    }


    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPostName() {
        return postName;
    }


    public String getPrice() {
        return price;
    }


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }


    public String getDescription() {
        return description;
    }


    public String getUserId() {
        return userId;
    }


    public String getAreaId() {
        return areaId;
    }


    public String getCategoryChildId() {
        return categoryChildId;
    }


    public Date getPostDate() {
        return postDate;
    }

    public void setPostDate(Date postDate) {
        this.postDate = postDate;
    }

    public List<String> getFileId() {
        return fileId;
    }

    public void setFileId(List<String> fileId) {
        this.fileId = fileId;
    }


}