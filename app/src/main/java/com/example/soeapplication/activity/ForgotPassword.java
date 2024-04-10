package com.example.soeapplication.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.soeapplication.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassword extends AppCompatActivity {
    TextInputEditText email_editText;
    TextInputLayout email_layout;
    ConstraintLayout ConfirmEmailLayout;
    FirebaseAuth mAuth;
    Button nextBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        UIValue();
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkEmail()) {
                    email_layout.setError(null);
                    ResendPassword();
                } else {
                    email_layout.setError("Vui lòng nhập Email hợp lệ!");
                }
            }
        });
    }
    private void ResendPassword(){
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Đang gửi email đến tài khoản "+ email_editText);
        progressDialog.setCancelable(false);
        progressDialog.show();
//        ConfirmEmailLayout.setVisibility(View.VISIBLE);
//        nextBtn.setVisibility(View.GONE);
        String email = email_editText.getText().toString();

        mAuth = FirebaseAuth.getInstance();
        mAuth.sendPasswordResetEmail(email).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                progressDialog.dismiss();
                Toast.makeText(ForgotPassword.this,"Email đã được gửi",Toast.LENGTH_SHORT).show();
            }
        });

    }
    private boolean checkEmail(){
        boolean flag = true;
        if(isEmpty(email_editText) || !isEmail(email_editText)){
            flag = false;
        }
        return flag;
    }
    private boolean isEmail(TextInputEditText text) {
        CharSequence email = text.getText().toString();
        return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }

    private boolean isEmpty(TextInputEditText text) {
        CharSequence str = text.getText().toString();
        return TextUtils.isEmpty(str);
    }
    private void UIValue(){
        ConfirmEmailLayout = findViewById(R.id.ConfirmEmailLayout);
        nextBtn = findViewById(R.id.NextBtn);
        email_editText = findViewById(R.id.Username);
        email_layout = findViewById(R.id.usernameLayout);
    }
}