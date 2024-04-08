package com.example.soeapplication.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.example.soeapplication.R;
import com.example.soeapplication.activity.Account;
import com.example.soeapplication.activity.Login;
import com.example.soeapplication.activity.Myprofile;

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


        infoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Tạo Intent để mở Myprofile activity
                Intent intent = new Intent(getActivity(), Myprofile.class);
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

        Button logoutButton = view.findViewById(R.id.button_info);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), Login.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        return view;
    }

}
