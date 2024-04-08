package com.example.soeapplication.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.example.soeapplication.R;
import com.example.soeapplication.activity.Myprofile;

public class UserFragment extends Fragment {

    public UserFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user, container, false);

        // Find the button by its ID
        Button infoButton = view.findViewById(R.id.button_info);

        // Set click listener for the button
        infoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to start Myprofile activity
                Intent intent = new Intent(getActivity(), Myprofile.class);
                startActivity(intent);
            }
        });

        return view;
    }
}
