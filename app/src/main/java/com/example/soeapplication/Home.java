package com.example.soeapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

public class Home extends AppCompatActivity {
    ConstraintLayout Logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        UIValue();
        Logo.setVisibility(View.VISIBLE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Logo.setVisibility(View.GONE);
                LoginActivity();
            }
        }, 2000);  // Ẩn layout sau 2 giây

    }
    private void LoginActivity(){
        Intent i = new Intent(this, Login.class);
        startActivity(i);
    }
    private void UIValue(){
        Logo = findViewById(R.id.Logo);
    }
}