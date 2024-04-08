package com.example.soeapplication;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;

public class ProductClass implements Serializable {
    String useruid ,name, infomation, cost, imageUrl, product_id;

    public ProductClass() {
    }
    public interface UsernameCallback {
        void onUsernameReceived(String username);
    }

    public ProductClass(String useruid, String name, String infomation, String cost, String imageUrl, String product_id) {
        this.useruid = useruid;
        this.name = name;
        this.infomation = infomation;
        this.cost = cost;
        this.imageUrl = imageUrl;
        this.product_id = product_id;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getUseruid() {
        return useruid;
    }

    public void setUseruid(String useruid) {
        this.useruid = useruid;
    }

    public String getName() {
        return name;
    }
    public String getLowerCaseName() {
        String lowerCase_name = name.toLowerCase();
        return lowerCase_name;
    }
    public String getShortName() {
        String short_name = name;
        String[] words = short_name.split("\\s+"); // Tách chuỗi thành mảng các từ
        if (words.length > 8) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < 25; i++) {
                sb.append(words[i]).append(" ");
            }
            sb.append("..."); // Thêm dấu ... vào cuối chuỗi
            short_name = sb.toString();
        }
        return short_name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInfomation() {
        return infomation;
    }
    public String getShortInfomation() {
        String short_infomation = infomation;
        String[] words = short_infomation.split("\\s+");
        if (words.length > 25) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < 25; i++) {
                sb.append(words[i]).append(" ");
            }
            sb.append("...");
            short_infomation = sb.toString();
        }
        return short_infomation;
    }
    public interface OnUsernameListener {
        void onUsernameReceived(String username);
    }
    public void getUsernameFromUserUid(String userUid, OnUsernameListener listener) {
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("user").child(userUid).child("name");
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String username = snapshot.getValue(String.class);
                    listener.onUsernameReceived(username);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Firebase", "Error: " + error.getMessage());
            }
        });
    }
    public void setInfomation(String infomation) {
        this.infomation = infomation;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
