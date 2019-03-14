package com.example.raovat.search;

import io.realm.RealmList;
import io.realm.RealmObject;

public class SearchHistory extends RealmObject {
    public SearchHistory() {
    }

    String str;

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }
}
