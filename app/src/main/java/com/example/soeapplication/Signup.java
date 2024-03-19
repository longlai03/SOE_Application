package com.example.soeapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class Signup extends AppCompatActivity {
    TextView btnLogin;
    TextInputLayout UsernameLayout, PasswordLayout, ConfirmPasswordLayout;
    TextInputEditText Username, Password, ConfirmPassword;


//    private

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        UIValue();
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void UIValue() {
        btnLogin = findViewById(R.id.Login);
        UsernameLayout = findViewById(R.id.usernameLayout);
        PasswordLayout = findViewById(R.id.passwordLayout);
        ConfirmPasswordLayout = findViewById(R.id.ConfrimPasswordLayout);
        Username = findViewById(R.id.Username);
        Password = findViewById(R.id.Password);
        ConfirmPassword = findViewById(R.id.ConfirmPassword);
    }
}