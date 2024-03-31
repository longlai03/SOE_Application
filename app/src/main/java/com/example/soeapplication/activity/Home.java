package com.example.soeapplication.activity;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.example.soeapplication.R;
import com.example.soeapplication.UserClass;
import com.example.soeapplication.adapter.ViewPagerAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Home extends AppCompatActivity {

    private FirebaseUser mUser;
    private FirebaseAuth mAuth;
    private FirebaseDatabase User_Database, Product_Database;
    private DatabaseReference User_reference, Product_reference;
    private ViewPager2 viewPager2;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        UIValue();
        ViewPagerAdapter view_adapter = new ViewPagerAdapter(Home.this);
        viewPager2.setAdapter(view_adapter);
        if (mUser == null) {
            LoginActivity();
        }

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                switch (position){
                    case 0:
                        bottomNavigationView.getMenu().findItem(R.id.bottom_home).setChecked(true);
                        break;
                    case 1:
                        bottomNavigationView.getMenu().findItem(R.id.bottom_chat).setChecked(true);
                        break;
                    case 2:
                        bottomNavigationView.getMenu().findItem(R.id.bottom_cart).setChecked(true);
                        break;
                    case 3:
                        bottomNavigationView.getMenu().findItem(R.id.bottom_user).setChecked(true);
                        break;
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
            }
        });
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if(id == R.id.bottom_home){
                    viewPager2.setCurrentItem(0);
                }
                else if(id == R.id.bottom_chat){
                    viewPager2.setCurrentItem(1);
                }
                else if(id == R.id.bottom_cart){
                    viewPager2.setCurrentItem(2);
                }
                else if(id == R.id.bottom_user){
                    viewPager2.setCurrentItem(3);
                }
                return true;
            }
        });
    }
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
                    } else {
                        finish();
                    }
                }
            });

    private void LoginActivity() {
        Intent i = new Intent(this, Login.class);
        mActivityResultLauncher.launch(i);
    }

    private void UIValue() {
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        viewPager2 = findViewById(R.id.home_viewpager);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
    }

    private void UserInformation() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Thông tin người dùng");
        builder.setMessage("Name: " + mUser.getDisplayName()
                + "\nEmail: " + mUser.getEmail()
                + "\nUid: " + mUser.getUid());
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog a = builder.create();
        a.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAuth.signOut();
    }
}