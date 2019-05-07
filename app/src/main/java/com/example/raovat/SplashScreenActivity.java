package com.example.raovat;



import android.content.Intent;
import android.os.Bundle;

import com.example.raovat.login.LoginActivity;

import androidx.appcompat.app.AppCompatActivity;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
