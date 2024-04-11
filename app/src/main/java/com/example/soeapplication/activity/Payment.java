package com.example.soeapplication.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.soeapplication.R;
import com.example.soeapplication.HelperClass.UserClass;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Payment extends AppCompatActivity {
    TextView txtSodu;
    ImageButton imgnaptien;
    FirebaseDatabase user_firebasedatabase;
    DatabaseReference user_databaseReference;
    FirebaseUser mUser;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        Anhxa();

        GetSoDu();
        imgnaptien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Payment.this);
                LayoutInflater inflater = getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.dialog_box_payin, null);
                builder.setView(dialogView);

                EditText edtAmount = dialogView.findViewById(R.id.cash_amount);

                builder.setPositiveButton("Xác nhận", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String amountStr = edtAmount.getText().toString();
                        if (!amountStr.isEmpty()) {
                            int amount = Integer.parseInt(amountStr);
                            UpdateBalance(amount);
                        } else {
                            Toast.makeText(Payment.this, "Vui lòng nhập số tiền", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }
                    }
                });
                builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }

        });

    }
    private void Anhxa(){
        txtSodu = findViewById(R.id.textViewsodu);
        imgnaptien = findViewById(R.id.imageButtonnaptien);

    }
    private void GetSoDu(){
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        Log.e("Payment", "mUser: "+mUser.getEmail());
        user_firebasedatabase = FirebaseDatabase.getInstance();
        user_databaseReference = user_firebasedatabase.getReference("user");
        if(mUser != null){
            user_databaseReference.child(mUser.getUid()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.exists()){
                        UserClass user = snapshot.getValue(UserClass.class);
                        txtSodu.setText(user.getBalance());
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        } else {
            Toast.makeText(Payment.this, "Yêu cầu đăng nhập", Toast.LENGTH_SHORT).show();
        }
    }
    private void UpdateBalance(int amount) {
        if (mUser != null) {
            // Lấy số dư hiện tại
            int currentBalance = Integer.parseInt(txtSodu.getText().toString());
            int updatedBalance = currentBalance + amount;
            String s = String.valueOf(updatedBalance);
            user_databaseReference.child(mUser.getUid()).child("balance").setValue(s);
        }
    }
}