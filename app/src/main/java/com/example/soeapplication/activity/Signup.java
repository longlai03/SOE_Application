package com.example.soeapplication.activity;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.soeapplication.R;
import com.example.soeapplication.HelperClass.UserClass;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Signup extends AppCompatActivity {
    TextView btnLogin;
    TextInputLayout UsernameLayout, PasswordLayout, ConfirmPasswordLayout, NameLayout;
    TextInputEditText Name, Password, ConfirmPassword, Address;
    Button SignupBtn;
    RadioButton radiobtnNu, radiobtnNam;
    RadioGroup radioGroup;
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    FirebaseDatabase Database;
    DatabaseReference reference;

//    private

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        UIValue();
        SignupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkDataEntered()) {
                    Log.e("this", "Okay");
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
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
    }

    private boolean checkDataEntered() {
        boolean flag = true;
        if (isEmpty(Name)) {
            NameLayout.setError("Cần nhập họ tên");
            flag = false;
        }
        if (isEmpty(Password)) {
            PasswordLayout.setError("Cần nhập mật khẩu");
            flag = false;
        }
        if (!isEmail(Address)) {
            UsernameLayout.setError("Vui lòng nhập email hợp lệ!");
            flag = false;
        }
        if (!isPassword(Password)) {
            PasswordLayout.setError("Mật khẩu cần ít nhất 8 ký tự và chứa cả số lẫn chữ cái");
            flag = false;
        }
        if (!Password.getText().toString().equals(ConfirmPassword.getText().toString())) {
            ConfirmPasswordLayout.setError("Hãy nhập mật khẩu xác nhận giống với mật khẩu của bạn");
            flag = false;
        }
        GT(flag);
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

    private boolean isPassword(TextInputEditText text) {
        CharSequence password = text.getText().toString();
        return !TextUtils.isDigitsOnly(password) && password.length() >= 8;
    }

    private void GT(boolean flag) {
        String s = GTValue();
        if (s.equals("")) {
            Toast t = Toast.makeText(this, "Bạn phải chọn giới tính ", Toast.LENGTH_SHORT);
            t.show();
            flag = false;
        }
    }
    private String GTValue(){
        String s = "";
        if (radiobtnNu.isChecked())
            s = radiobtnNu.getText().toString();
        else if (radiobtnNam.isChecked()) {
            s = radiobtnNam.getText().toString();
        }
        return s;
    }

    private void DangKy() {
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Đang đăng ký...");
        progressDialog.show();
        String name = Name.getText().toString().trim();
        String username = Address.getText().toString().trim();
        String password = Password.getText().toString().trim();
        String sex = GTValue();
        Log.e("this", "Username: " + username);
        Log.e("this", "Password: " + password);
        mAuth.createUserWithEmailAndPassword(username, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    mUser = mAuth.getCurrentUser();
                    progressDialog.dismiss();
                    Toast t = Toast.makeText(Signup.this, "Đăng ký thành công", Toast.LENGTH_SHORT);
                    t.show();
                    setDatabase(mUser, sex, name);

                    finish();
                } else {
                    progressDialog.dismiss();
                    Log.e("this", "Message: " + task.getException());
                    UsernameLayout.setError("Tài khoản này đã tồn tại");

                }
            }
        });
    }

    private void setDatabase(FirebaseUser user, String sex, String name) {
        Database = FirebaseDatabase.getInstance();
        reference = Database.getReference("user");

        UserClass userClass = new UserClass(user.getUid(), sex, name, user.getEmail(),"","0");
        reference.child(user.getUid()).setValue(userClass);
    }

}