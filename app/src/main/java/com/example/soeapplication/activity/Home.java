package com.example.soeapplication.activity;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import com.example.soeapplication.R;
import com.example.soeapplication.UserClass;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Home extends AppCompatActivity {
    ConstraintLayout Logo;
    FirebaseUser mUser;
    FirebaseDatabase Database;
    DatabaseReference reference;
    private ActivityResultLauncher<Intent> mActivityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult()
            , new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == RESULT_OK) {
                        Intent intent = result.getData();
                        //Lay du lieu nguoi dung o day
                        if (intent != null && intent.hasExtra("firebaseUser")) {
                            mUser = intent.getParcelableExtra("firebaseUser");
                            Log.e("This", "mUser = " + mUser.getEmail());
                            UserInformation();
                        }
                    }
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        UIValue();

//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference myRef = database.getReference("message");

//        myRef.setValue("Hello, World!");

        Logo.setVisibility(View.VISIBLE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Logo.setVisibility(View.GONE);
                LoginActivity();
            }
        }, 2000);  // Ẩn layout sau 2 giây

    }

    private void LoginActivity() {
        Intent i = new Intent(this, Login.class);
        mActivityResultLauncher.launch(i);
    }

    private void UIValue() {
        Logo = findViewById(R.id.Logo);
        mUser = FirebaseAuth.getInstance().getCurrentUser();
    }
    private void UserInformation(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Thông tin người dùng");
        builder.setMessage("Name: "+mUser.getDisplayName()
                +"\nEmail: "+mUser.getEmail()
                +"\nUid: "+mUser.getUid());
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog a = builder.create();
        a.show();
    }

}