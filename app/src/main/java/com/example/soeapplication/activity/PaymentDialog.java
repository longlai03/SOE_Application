package com.example.soeapplication.activity;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.soeapplication.HelperClass.UserClass;
import com.example.soeapplication.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PaymentDialog extends Dialog {
    String totalPrice;

    public PaymentDialog(@NonNull Context context, String totalPrice) {
        super(context);
        this.totalPrice = totalPrice;
    }

    private TextView total_bill_product_cost, user_balance, user_balance_left;
    private Button bill_back_button, buy_button;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private FirebaseDatabase user_firebaseDatabase;
    private DatabaseReference user_databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_payment);
        AnhXa();
        GetSoDu();
        buy_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CaculateBill();
            }
        });
        bill_back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    private void AnhXa() {
        total_bill_product_cost = findViewById(R.id.total_bill_product_cost);
        total_bill_product_cost.setText(totalPrice);
        user_balance = findViewById(R.id.user_balance);
        user_balance_left = findViewById(R.id.user_balance_left);
        bill_back_button = findViewById(R.id.bill_back_button);
        buy_button = findViewById(R.id.buy_button);
    }

    private void GetSoDu() {
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        Log.e("PaymentDialog", "userEmail: " + mUser.getEmail());

        if (mUser != null) {
            user_firebaseDatabase = FirebaseDatabase.getInstance();
            user_databaseReference = user_firebaseDatabase.getReference("user").child(mUser.getUid());
            user_databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        UserClass user = snapshot.getValue(UserClass.class);
                        user_balance.setText(user.getBalance());
                        int balance = Integer.parseInt(user_balance.getText().toString());
                        int product_cost = Integer.parseInt(total_bill_product_cost.getText().toString());

                        int balance_left = balance - product_cost;
                        user_balance_left.setText(String.valueOf(balance_left));
                    } else {
                        Toast.makeText(getContext(), "Không thể xác minh số dư người dùng", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }
    }


    private void CaculateBill() {
        if (TextUtils.isDigitsOnly(user_balance.getText().toString()) && TextUtils.isDigitsOnly(total_bill_product_cost.getText().toString())) {
            mAuth = FirebaseAuth.getInstance();
            mUser = mAuth.getCurrentUser();
            user_firebaseDatabase = FirebaseDatabase.getInstance();
            user_databaseReference = user_firebaseDatabase.getReference("user").child(mUser.getUid());
            int balance = Integer.parseInt(user_balance.getText().toString());
            int product_cost = Integer.parseInt(total_bill_product_cost.getText().toString());

            int balance_left = balance - product_cost;
            user_balance_left.setText(String.valueOf(balance_left));
            if (balance_left < 0) {
                Toast.makeText(getContext(), "Số dư tài khoản người dùng hiện không đủ", Toast.LENGTH_SHORT).show();
            } else {
                user_databaseReference.child("balance").setValue(String.valueOf(balance_left)).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.e("CaculateBill", "balance left: " + balance_left);
                        Toast.makeText(getContext(), "Thanh toán thành công", Toast.LENGTH_SHORT).show();
                        dismiss();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(), "Thanh toán thất bại", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        } else {
            Toast.makeText(getContext(), "Lỗi khi lấy dữ liêu hóa đơn", Toast.LENGTH_SHORT).show();
        }
    }
}
