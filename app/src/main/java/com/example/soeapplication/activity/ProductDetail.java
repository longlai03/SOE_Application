package com.example.soeapplication.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.soeapplication.HelperClass.ProductClass;
import com.example.soeapplication.R;

import java.io.Serializable;

public class ProductDetail extends AppCompatActivity {
    TextView productName, productCost, productInfo, productSeller;
    ImageView productImage;
    ImageButton backButton;
    Button addtoCart, buyProduct;
    ProductClass product;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        AnhXa();

        product = (ProductClass) getIntent().getSerializableExtra("product");
        if(product != null){
            Glide.with(this).load(product.getImageUrl()).into(productImage);
            productName.setText(product.getName());
            productCost.setText(product.getCost()+"đ");
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

            }
        });
        buyProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ProductDetail.this, BuyProduct.class);
                i.putExtra("product", (Serializable) product);
                startActivity(i);
            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private void AnhXa(){
        backButton = findViewById(R.id.back_button);
        productName = findViewById(R.id.Product_Name);
        productCost = findViewById(R.id.Product_cost);
        productInfo = findViewById(R.id.Product_info);
        productSeller = findViewById(R.id.Product_seller);
        productImage = findViewById(R.id.product_image);
        addtoCart = findViewById(R.id.addtoCart_button);
        buyProduct = findViewById(R.id.buy_button);
    }
}