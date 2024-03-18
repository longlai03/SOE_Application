package com.example.soeapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class Login extends AppCompatActivity {
    TextInputEditText Username, Password;
    TextInputLayout UsernameLayout, PasswordLayout;
    TextView btnForgotPassword, btnSignUp, btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        UIValue();
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Login.this, Signup.class);
                startActivity(i);
            }
        });
        btnForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Login.this, ForgotPassword.class);
                startActivity(i);
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkUserName();
            }
        });
    }

    private void UIValue() {
        Username = findViewById(R.id.Username);
        Password = findViewById(R.id.Password);
        UsernameLayout = findViewById(R.id.usernameLayout);
        PasswordLayout = findViewById(R.id.passwordLayout);
        btnForgotPassword = findViewById(R.id.ForgotPassword);
        btnSignUp = findViewById(R.id.SignUp);
        btnLogin = findViewById(R.id.LoginBtn);
    }

    void checkUserName() {
        boolean isValid = true;
        if (isEmpty(Username)) {
            UsernameLayout.setError("Không được để trống tên đăng nhập");

            //HA hoc code ne
            isValid = false;
        } else {
            if (!isEmail(Username)) {
                UsernameLayout.setError("Email không tồn tại");
            }
        }
        if (isEmpty(Password)) {
            PasswordLayout.setError("Không được để trống mật khẩu");
            isValid = false;
        }
        if (isValid) {
            String usernameValue = Username.getText().toString();
            String passwordValue = Password.getText().toString();
            if (usernameValue.equals("test@test.com") && passwordValue.equals("12345678")) {
                //Xu ly
                finish();
            } else {
                Toast t = Toast.makeText(this, "Nhập sai tên tài khoản hoặc mật khẩu", Toast.LENGTH_SHORT);
                t.show();
            }
        }
        Log.e("ABC","--> Username:"+ Username.getText().toString());
    }

    boolean isEmail(TextInputEditText text) {
        CharSequence email = text.getText().toString();
        return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }

    boolean isEmpty(TextInputEditText text) {
        CharSequence str = text.getText().toString();
        return TextUtils.isEmpty(str);
    }
}