package com.example.soeapplication.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.soeapplication.R;

public class ForgotPassword extends AppCompatActivity {
    ConstraintLayout ConfirmEmailLayout;
    Button nextBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        UIValue();
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConfirmEmailLayout.setVisibility(View.VISIBLE);
                nextBtn.setVisibility(View.GONE);
            }
        });
    }
    private void UIValue(){
        ConfirmEmailLayout = findViewById(R.id.ConfirmEmailLayout);
        nextBtn = findViewById(R.id.NextBtn);
    }
}