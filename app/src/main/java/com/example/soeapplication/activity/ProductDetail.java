package com.example.soeapplication.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.soeapplication.HelperClass.CartProductClass;
import com.example.soeapplication.HelperClass.ProductClass;
import com.example.soeapplication.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ProductDetail extends AppCompatActivity {
    TextView productName, productCost, productInfo, productSeller;
    ImageView productImage;
    ImageButton backButton;
    Button addtoCart;
    ProductClass product;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        AnhXa();

        product = (ProductClass) getIntent().getSerializableExtra("product");
        if (product != null) {
            Glide.with(this).load(product.getImageUrl()).into(productImage);
            productName.setText(product.getName());
            productCost.setText(product.getCost() + "đ");
            productInfo.setText(product.getInfomation());
            product.getUsernameFromUserUid(product.getUseruid(), new ProductClass.OnUsernameListener() {
                @Override
                public void onUsernameReceived(String username) {
                    productSeller.setText(username);
                }
            });
        }
        addtoCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(product != null) {
                    FirebaseAuth mAuth = FirebaseAuth.getInstance();
                    FirebaseUser mUser = mAuth.getCurrentUser();
                    CartProductClass cartProduct = new CartProductClass();
                    cartProduct.setUseruid(mUser.getUid());
                    cartProduct.setName(product.getName());
                    cartProduct.setCost(product.getCost());
                    cartProduct.setImageUrl(product.getImageUrl());
                    cartProduct.setProduct_id(product.getProduct_id());
                    cartProduct.setProduct_quantity("1");

                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                    String currentDate = sdf.format(new Date());
                    cartProduct.setDate_of_order(currentDate);

                    FirebaseDatabase cart_firebaseDatabase = FirebaseDatabase.getInstance();
                    DatabaseReference cart_databaseReference = cart_firebaseDatabase.getReference("cart").child(mUser.getUid()).child(cartProduct.getProduct_id());

                    cart_databaseReference.setValue(cartProduct).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(ProductDetail.this, "Sản phẩm đã được thêm vào giỏ hàng", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(ProductDetail.this, "Lỗi khi thêm sản phẩm vào giỏ hàng", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    });
                } else {
                    Toast.makeText(ProductDetail.this, "Lỗi khi thêm sản phẩm vào giỏ hàng", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void AnhXa() {
        backButton = findViewById(R.id.back_button);
        productName = findViewById(R.id.Product_Name);
        productCost = findViewById(R.id.Product_cost);
        productInfo = findViewById(R.id.Product_info);
        productSeller = findViewById(R.id.cart_product_seller);
        productImage = findViewById(R.id.product_image);
        addtoCart = findViewById(R.id.addtoCart_button);
    }
}