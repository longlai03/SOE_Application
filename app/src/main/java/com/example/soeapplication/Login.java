package com.example.soeapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class Login extends AppCompatActivity {
    TextInputEditText Username, Password;
    TextInputLayout UsernameLayout, PasswordLayout;
    TextView btnForgotPassword, btnSignUp;
    Button btnLogin;
    FirebaseAuth mAuth;

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
                if(checkUserName()){
                    DangNhap();
                }
            }
        });
        Username.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    UsernameLayout.setError(null);
                }
            }
        });
        Password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    PasswordLayout.setError(null);
                }
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
        mAuth = FirebaseAuth.getInstance();
    }


    private boolean checkUserName() {
        boolean flag = true;
        if (isEmpty(Username)) {
            UsernameLayout.setError("Không được để trống tên đăng nhập");
            flag = false;
        } else {
            if (!isEmail(Username)) {
                UsernameLayout.setError("Email không tồn tại");
            }
        }
        if (isEmpty(Password)) {
            PasswordLayout.setError("Không được để trống mật khẩu");
            flag = false;
        }
        Log.e("ABC","--> Username:"+ Username.getText().toString());
        return flag;
    }
    private void DangNhap(){
        String username = Username.getText().toString().trim();
        String password = Password.getText().toString().trim();
        mAuth.signInWithEmailAndPassword(username, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    finish();
                }
                else{
                    UsernameLayout.setError("Nhập sai tài khoản hoặc mật khẩu");
                }
            }
        });
    }


    private boolean isEmail(TextInputEditText text) {
        CharSequence email = text.getText().toString();
        return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }

    private boolean isEmpty(TextInputEditText text) {
        CharSequence str = text.getText().toString();
        return TextUtils.isEmpty(str);
    }
}