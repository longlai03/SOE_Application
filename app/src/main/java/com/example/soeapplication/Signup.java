package com.example.soeapplication;


import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class Signup extends AppCompatActivity {
    TextView btnLogin;
    TextInputLayout UsernameLayout, PasswordLayout, ConfirmPasswordLayout, NameLayout;
    TextInputEditText Name, Password, ConfirmPassword, Address;
    Button SignupBtn;
    RadioButton radiobtnNu, radiobtnNam;
    RadioGroup radioGroup;


//    private

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        UIValue();
        SignupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkDataEntered()){
                    DangKy();
                }
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Name.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                NameLayout.setError(null);
            }
        });
        Address.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                UsernameLayout.setError(null);
            }
        });
        Password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                PasswordLayout.setError(null);
            }
        });
        ConfirmPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                ConfirmPasswordLayout.setError(null);
            }
        });
    }

    private void UIValue() {
        btnLogin = findViewById(R.id.Login);
        UsernameLayout = findViewById(R.id.usernameLayout);
        PasswordLayout = findViewById(R.id.passwordLayout);
        ConfirmPasswordLayout = findViewById(R.id.ConfrimPasswordLayout);
        Name = findViewById(R.id.Name);
        Address = findViewById(R.id.Username);
        Password = findViewById(R.id.Password);
        ConfirmPassword = findViewById(R.id.ConfirmPassword);
        SignupBtn = findViewById(R.id.SignupBtn);
        radiobtnNu = findViewById(R.id.radiobtnNu);
        radiobtnNam = findViewById(R.id.radiobtnNam);
        radioGroup = findViewById(R.id.radioGroup);
        NameLayout = findViewById(R.id.nameLayout);
    }

    boolean checkDataEntered() {
        boolean flag = true;
        if (isEmpty(Name)) {
            NameLayout.setError("Cần nhập họ tên");
            flag = false;
        }
        if (isEmpty(Password)) {
            PasswordLayout.setError("Cần nhập mật khẩu");
            flag = false;
        }
        if (isEmpty(ConfirmPassword)) {
            PasswordLayout.setError("Cần nhập mật khẩu");
            flag = false;
        }
        if (isEmail(Address) == false) {
            UsernameLayout.setError("Vui lòng nhập email hợp lệ!");
            flag = false;
        }
        GT(flag);
        return flag;
    }

    boolean isEmail(TextInputEditText text) {
        CharSequence email = text.getText().toString();
        return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }

    boolean isEmpty(TextInputEditText text) {
        CharSequence str = text.getText().toString();
        return TextUtils.isEmpty(str);
    }

    void GT(boolean flag) {
        String s = "";
        if (radiobtnNu.isChecked())
            s = radiobtnNu.getText().toString();
        else if (radiobtnNam.isChecked()) {
            s = radiobtnNam.getText().toString();
        }
        if (s.equals("")) {
            Toast t = Toast.makeText(this, "Bạn phải chọn giới tính ", Toast.LENGTH_SHORT);
            t.show();
            flag = false;
        }
    }
    void DangKy(){
        String username = Address.getText().toString();
        String password = Password.getText().toString();
    }
}