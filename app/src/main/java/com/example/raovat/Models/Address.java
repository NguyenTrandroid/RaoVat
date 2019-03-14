package com.example.raovat.Models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Address {

@SerializedName("status")
@Expose
private Boolean status;
@SerializedName("data")
@Expose
private List<Data2> data = null;

public Boolean getStatus() {
return status;
}

public void setStatus(Boolean status) {
this.status = status;
}

public List<Data2> getData() {
return data;
}

public void setData(List<Data2> data) {
this.data = data;
}

}