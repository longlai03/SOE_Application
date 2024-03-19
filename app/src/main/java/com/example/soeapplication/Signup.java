package com.example.soeapplication;


import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class Signup extends AppCompatActivity {
    TextView btnLogin;
    TextInputLayout UsernameLayout, PasswordLayout, ConfirmPasswordLayout;
    TextInputEditText Name, Password, ConfirmPassword, Address;
    Button LoginBtn;
    RadioButton radiobtnNu,radiobtnNam;
    RadioGroup radioGroup;


//    private

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        UIValue();
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkDataEntered(Name, Password , ConfirmPassword, Address);
                GT(radiobtnNu,radiobtnNam);
                finish();
            }
        });

    }

    private void UIValue() {
        btnLogin = findViewById(R.id.Login);
        UsernameLayout = findViewById(R.id.usernameLayout);
        PasswordLayout = findViewById(R.id.passwordLayout);
        ConfirmPasswordLayout = findViewById(R.id.ConfrimPasswordLayout);
        Name = findViewById(R.id.Name);
        Address = findViewById(R.id.Address);
        Password = findViewById(R.id.Password);
        ConfirmPassword = findViewById(R.id.ConfirmPassword);
        LoginBtn = findViewById(R.id.LoginBtn);
        radiobtnNu = findViewById(R.id.radiobtnNu);
        radiobtnNam = findViewById(R.id.radiobtnNam);
        radioGroup =  findViewById(R.id.radioGroup);


    }

    void checkDataEntered(TextInputEditText Name,TextInputEditText Password,TextInputEditText ConfirmPassword,TextInputEditText Address){
        if (isEmpty(Name)){
            Toast t = Toast.makeText(this, "Bạn phải nhập tên để đăng ký", Toast.LENGTH_SHORT);
            t.show();
        }
        if (isEmpty(Password)) {
            Password.setError("Cần nhập mật khẩu");
        }
        if (isEmpty(ConfirmPassword)) {
            Password.setError("Cần nhập mật khẩu");
        }
        if (isEmail(Address) == false) {
            Address.setError("Vui lòng nhập email hợp lệ!");
        }

    }
    boolean isEmail(TextInputEditText text) {
        CharSequence email = text.getText().toString();
        return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }

    boolean isEmpty(TextInputEditText text) {
        CharSequence str = text.getText().toString();
        return TextUtils.isEmpty(str);
    }

    void GT(RadioButton radiobtnNu,RadioButton radiobtnNam){
        String s ="";
        if(radiobtnNu.isChecked())
            s+= radiobtnNu.getText().toString();
        else if(radiobtnNam.isChecked()){
            s+= radiobtnNam.getText().toString();
        }
        if (s.equals("")) {
            Toast t = Toast.makeText(this, "Bạn phải chọn giới tính ", Toast.LENGTH_SHORT);
        }
    }

}