package com.example.soeapplication.fragment;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.example.soeapplication.R;
import com.example.soeapplication.activity.Account;
import com.example.soeapplication.activity.MyProfile;

public class UserFragment extends Fragment {

    public UserFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user, container, false);

        Button infoButton = view.findViewById(R.id.button_info);
        Button accountButton = view.findViewById(R.id.button_setting);
        Button helpButton = view.findViewById(R.id.button_help);

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
                                    // Mở liên kết đến trang Facebook
                                    openLink("https://www.facebook.com/hyeng.laz/?locale=vi_VN");
                                })
                                .setNegativeButton("Instagram", (dialog, which) -> {
                                    // Mở liên kết đến trang Instagram
                                    openLink("https://www.instagram.com/https://www.instagram.com/_huogw.lmaz/");
                                })
                                .setNeutralButton("Hủy", (dialog, which) -> {
                                    // Đóng AlertDialog nếu người dùng chọn hủy
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
        }
