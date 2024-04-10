package com.example.soeapplication.fragment;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.soeapplication.R;
import com.example.soeapplication.UserClass;
import com.example.soeapplication.activity.Account;
import com.example.soeapplication.activity.MyProfile;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class UserFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_USER = "user";

    // TODO: Rename and change types of parameters


    public static HomeFragment newInstance(UserClass user) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_USER, user);
        fragment.setArguments(args);
        return fragment;
    }

    public UserFragment() {
        // Required empty public constructor
    }

    private TextView userEmail,userName;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private FirebaseDatabase user_database;
    private DatabaseReference user_databaseReference;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user, container, false);
        Button infoButton = view.findViewById(R.id.button_info);
        Button accountButton = view.findViewById(R.id.button_setting);
        Button helpButton = view.findViewById(R.id.button_help);
        userEmail = view.findViewById(R.id.textView_useremail);
        userName = view.findViewById(R.id.textView_username);
        CallingUpdateUser();


        infoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Tạo Intent để mở Myprofile activity
                Intent intent = new Intent(getActivity(), MyProfile.class);
                startActivity(intent);
            }
        });


        accountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Tạo Intent để mở Account activity
                Intent intent = new Intent(getActivity(), Account.class);
                startActivity(intent);
            }
        });


        helpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Tạo một AlertDialog chứa các liên kết đến Facebook và Instagram
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Trợ giúp và hỗ trợ")
                        .setMessage("Liên kết đến trang Facebook và Instagram:")
                        .setPositiveButton("Facebook", (dialog, which) -> {
                            openLink("https://www.facebook.com/hyeng.laz/?locale=vi_VN");
                        })
                        .setNegativeButton("Instagram", (dialog, which) -> {
                            openLink("https://www.instagram.com/_huogw.lmaz?igsh=MXN1Ym5wNWw1N2RjMw==");

                        })
                        .setNeutralButton("Hủy", (dialog, which) -> {
                            dialog.dismiss();
                        })
                        .show();
            }
        });

        return view;
    }

    // Phương thức để mở liên kết bằng trình duyệt
    private void openLink(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(intent);
    }
    private void CallingUpdateUser() {
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        Log.e("UserFragment","userEmail: "+mUser.getEmail());
        Log.e("UserFragment","userUid: "+mUser.getUid());
        user_database = FirebaseDatabase.getInstance();
        user_databaseReference = user_database.getReference("user");
        user_databaseReference.child(mUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    UserClass userClass = snapshot.getValue(UserClass.class);
                    Log.e("Home", "CallingUpdate");
                    Log.e("Home", "UserEmail" + userClass.getEmail());
                    userEmail.setText(userClass.getEmail());
                    Log.e("UserFragment", "Email: "+userClass.getEmail());
                    Log.e("UserName", "Name: "+userClass.getName());
                    userName.setText(userClass.getName());
                } else {
                    Log.d("Firebase", "User data does not exist");
                    userEmail.setText("");
                    userName.setText("Người dùng chưa đăng nhập");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        CallingUpdateUser();
    }
}
