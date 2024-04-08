package com.example.soeapplication.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.soeapplication.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Account extends AppCompatActivity {
    AppCompatButton logOutButton;
    FirebaseAuth mAuth;
    FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        logOutButton = findViewById(R.id.button_logout);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
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