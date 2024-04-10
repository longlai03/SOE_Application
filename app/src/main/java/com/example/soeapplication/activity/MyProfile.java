package com.example.soeapplication.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.soeapplication.R;
import com.example.soeapplication.UserClass;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MyProfile extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private FirebaseDatabase user_database;
    private DatabaseReference user_databaseReference;
    private TextInputEditText emailEditText, passwordEditText, countryEditText, districtEditText, streetEditText;
    private TextInputLayout emailLayoutEditText, passwordLayoutEditText, countryLayoutEditText, districtLayoutEditText, streetLayoutEditText, accountLayoutEdittext;
    private Button updateButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myprofile);
        AnhXa();
        CallingUpdateUser();
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mUser == null) {
                    Toast.makeText(MyProfile.this, "Hãy đăng nhập để sử dụng chức năng này", Toast.LENGTH_SHORT).show();
                    finish();
                } else if (CheckDataEnter()) {
                    SetUpdateUser();
                }
            }
        });

    }

    private void CallingUpdateUser() {
        if (mUser != null) {
            Log.e("UserFragment", "userEmail: " + mUser.getEmail());
            Log.e("UserFragment", "userUid: " + mUser.getUid());
            user_databaseReference.child(mUser.getUid()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        UserClass userClass = snapshot.getValue(UserClass.class);

                        emailEditText.setText(userClass.getEmail());
                        Log.e("UserFragment", "Email: " + userClass.getEmail());
                        Log.e("UserName", "Name: " + userClass.getName());
                        passwordEditText.setText("********");
                    } else {
                        emailEditText.setText(null);
                        passwordEditText.setText(null);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        } else {
            emailEditText.setText(null);
            passwordEditText.setText(null);
        }
    }

    private void SetUpdateUser() {
        ProgressDialog progressDialog = new ProgressDialog(MyProfile.this);
        progressDialog.setTitle("Đang cập nhật...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        String address = streetEditText.getText().toString() + ", " +
                districtEditText.getText().toString() + ", " +
                countryEditText.getText().toString();
        //Cap nhat dia chi
        Log.e("MyProfile", "user: " + mUser);
        user_databaseReference.child(mUser.getUid()).child("address").setValue(address).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private boolean CheckDataEnter() {
        boolean flag = true;
        TextInputEditText editText[] = {countryEditText, districtEditText, streetEditText};
        TextInputLayout layout[] = {countryLayoutEditText, districtLayoutEditText, streetLayoutEditText};
        for (int i = 0; i < editText.length; i++) {
            if (isEmpty(editText[i])) {
                layout[i].setError("Không được để trống");
                flag = false;
            }
        }
        return flag;
    }

    private boolean isEmpty(TextInputEditText text) {
        CharSequence str = text.getText().toString();
        return TextUtils.isEmpty(str);
    }

    private void AnhXa() {
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        countryEditText = findViewById(R.id.countryEditText);
        districtEditText = findViewById(R.id.districtEditText);
        streetEditText = findViewById(R.id.streetEditText);
        countryLayoutEditText = findViewById(R.id.countryTextInputLayout);
        districtLayoutEditText = findViewById(R.id.districtTextInputLayout);
        streetLayoutEditText = findViewById(R.id.streetTextInputLayout);
        updateButton = findViewById(R.id.update_button);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        user_database = FirebaseDatabase.getInstance();
        user_databaseReference = user_database.getReference("user");
    }
}