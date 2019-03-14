package com.example.raovat.Models;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum {

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
    private String postDate;
    @SerializedName("__v")
    @Expose
    private Integer v;

    public List<String> getPostUrl() {
        return postUrl;
    }

    public void setPostUrl(List<String> postUrl) {
        this.postUrl = postUrl;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public List<String> getFileId() {
        return fileId;
    }

    public void setFileId(List<String> fileId) {
        this.fileId = fileId;
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

    public void setPostName(String postName) {
        this.postName = postName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
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

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getCategoryChildId() {
        return categoryChildId;
    }

    public void setCategoryChildId(String categoryChildId) {
        this.categoryChildId = categoryChildId;
    }

    public String getPostDate() {
        return postDate;
    }

    public void setPostDate(String postDate) {
        this.postDate = postDate;
    }

    public Integer getV() {
        return v;
    }

    public void setV(Integer v) {
        this.v = v;
    }

}