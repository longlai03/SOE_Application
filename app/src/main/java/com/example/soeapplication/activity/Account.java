package com.example.soeapplication.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.soeapplication.R;
import com.example.soeapplication.HelperClass.UserClass;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Account extends AppCompatActivity {
    AppCompatButton logOutButton;
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    FirebaseDatabase user_database;
    DatabaseReference user_databaseReference;
    TextView username, useremail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        logOutButton = findViewById(R.id.button_logout);
        username = findViewById(R.id.textView_username);
        useremail = findViewById(R.id.textView_useremail);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        user_database = FirebaseDatabase.getInstance();
        user_databaseReference = user_database.getReference("user");
        user_databaseReference.child(mUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    UserClass userClass = snapshot.getValue(UserClass.class);
                    username.setText(userClass.getName());
                    useremail.setText(userClass.getEmail());
                } else {
                    username.setText("Người dùng chưa đăng nhập");
                    useremail.setText("");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        logOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                if(mUser != null){
                    mUser = null;
                }
                Intent intent = new Intent(Account.this, Login.class);
                startActivity(intent);
                finish();
            }
        });
    }
}