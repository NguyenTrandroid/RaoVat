package com.example.raovat.Models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CategoryChild {

@SerializedName("Posts")
@Expose
private List<Post> posts = null;
@SerializedName("_id")
@Expose
private String id;
@SerializedName("CategoryChildName")
@Expose
private String categoryChildName;
@SerializedName("CategoryChildUrl")
@Expose
private String categoryChildUrl;
@SerializedName("CategoryParent")
@Expose
private String categoryParent;
@SerializedName("__v")
@Expose
private Integer v;

public List<Post> getPosts() {
return posts;
}

public void setPosts(List<Post> posts) {
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

public String getCategoryChildUrl() {
return categoryChildUrl;
}

public void setCategoryChildUrl(String categoryChildUrl) {
this.categoryChildUrl = categoryChildUrl;
}

public String getCategoryParent() {
return categoryParent;
}

public void setCategoryParent(String categoryParent) {
this.categoryParent = categoryParent;
}

public Integer getV() {
return v;
}

public void setV(Integer v) {
this.v = v;
}

}