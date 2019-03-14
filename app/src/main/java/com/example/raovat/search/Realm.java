package com.example.raovat.search;

import android.app.Application;

import io.realm.RealmConfiguration;

public class Realm extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        io.realm.Realm.init(this);
        RealmConfiguration configuration = new RealmConfiguration.Builder().build();
        io.realm.Realm.setDefaultConfiguration(configuration);
    }
}
