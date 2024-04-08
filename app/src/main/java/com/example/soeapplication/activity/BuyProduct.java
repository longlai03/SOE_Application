package com.example.soeapplication.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.soeapplication.ProductClass;
import com.example.soeapplication.R;

public class BuyProduct extends AppCompatActivity {
    TextView productTitle, productShortInfo, DateOrder, productCost, voucherCost, deliveryCost, totalCost, totalCost2;
    Button buyProduct;
    ProductClass product;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_product);

        AnhXa();
    }
    private void AnhXa(){
        productTitle = findViewById(R.id.product_title);
        productShortInfo = findViewById(R.id.Product_shortinfo);

    }
}