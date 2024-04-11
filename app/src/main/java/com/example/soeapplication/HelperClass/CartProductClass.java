package com.example.soeapplication.HelperClass;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CartProductClass {
    String useruid ,name, cost, imageUrl, product_id, product_quantity, date_of_order;

    public CartProductClass(String useruid, String name, String cost, String imageUrl, String product_id, String product_quantity, String date_of_order) {
        this.useruid = useruid;
        this.name = name;
        this.cost = cost;
        this.imageUrl = imageUrl;
        this.product_id = product_id;
        this.product_quantity = product_quantity;
        this.date_of_order = date_of_order;
    }

    public CartProductClass() {
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

    public void setName(String name) {
        this.name = name;
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

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getProduct_quantity() {
        return product_quantity;
    }

    public void setProduct_quantity(String product_quantity) {
        this.product_quantity = product_quantity;
    }

    public String getDate_of_order() {
        return date_of_order;
    }

    public void setDate_of_order(String date_of_order) {
        this.date_of_order = date_of_order;
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
}
