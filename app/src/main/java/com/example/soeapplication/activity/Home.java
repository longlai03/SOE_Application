package com.example.soeapplication.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.soeapplication.R;
import com.example.soeapplication.HelperClass.UserClass;
import com.example.soeapplication.adapter.ViewPagerAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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
        } else {
            CallingUpdateUser();
        }
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                switch (position) {
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
                if (id == R.id.bottom_home) {
                    viewPager2.setCurrentItem(0);
                } else if (id == R.id.bottom_chat) {
                    viewPager2.setCurrentItem(1);
                } else if (id == R.id.bottom_cart) {
                    viewPager2.setCurrentItem(2);
                } else if (id == R.id.bottom_user) {
                    viewPager2.setCurrentItem(3);
                }
                return true;
            }
        });
    }

    private final ActivityResultLauncher<Intent> mActivityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult()
            , new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == RESULT_OK) {
                        Intent intent = result.getData();
                        //Lay du lieu nguoi dung o day
                        if (intent != null && intent.hasExtra("firebaseUser")) {
                            mUser = intent.getParcelableExtra("firebaseUser");
                            Log.e("This", "mUser = " + mUser.getEmail());
                            if (mUser != null) {
                                CallingUpdateUser();
                            }
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

    private void CallingUpdateUser() {
        User_Database = FirebaseDatabase.getInstance();
        User_reference = User_Database.getReference("user");
        if (mUser != null) {
            User_reference.child(mUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        UserClass userClass = snapshot.getValue(UserClass.class);
                        Log.e("Home", "CallingUpdate");
                        Log.e("Home", "UserEmail" + userClass.getEmail());
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        } else {
            Toast.makeText(this,"Nguoi dung chua dang nhap!",Toast.LENGTH_SHORT).show();
        }
    }

    private void UIValue() {
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        viewPager2 = findViewById(R.id.home_viewpager);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("Home", "CallingResume");
        Log.e("Home", "mUser = " + mUser.getEmail());
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        CallingUpdateUser();
    }
}