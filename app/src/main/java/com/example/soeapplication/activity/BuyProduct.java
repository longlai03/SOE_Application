package com.example.soeapplication.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.soeapplication.HelperClass.ProductClass;
import com.example.soeapplication.R;

public class BuyProduct extends AppCompatActivity {
    TextView productTitle, productSeller, DateOrder, productCost, voucherCost, deliveryCost, totalCost, totalCost2, tvplus, tvminus;
    Button buyProduct;
    ImageButton backbutton;
    ImageView productImage;
    ProductClass product;
    EditText productQuantity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_product);

//        AnhXa();

//        product = (ProductClass) getIntent().getSerializableExtra("product");
//        if (product != null) {
////            Glide.with(this).load(product.getImageUrl()).into(productImage);
////            productTitle.setText(product.getName());
////            productShortInfo.setText(product.getShortInfomation());
////            productCost.setText(product.getCost());
////            voucherCost.setText("0");
////            deliveryCost.setText("0");
//        }

//        backbutton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });
//        tvplus.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (TextUtils.isDigitsOnly(productCost.getText().toString())) {
//                    String currentQuantity = productQuantity.getText().toString();
//
//                    if (TextUtils.isDigitsOnly(currentQuantity)) {
//                        int quantity = Integer.parseInt(currentQuantity);
//                        quantity++;
//                        if (quantity > 100) {
//                            quantity = 0;
//                        }
//                        productQuantity.setText(String.valueOf(quantity));
//                        totalCost.setText(String.valueOf(GiaSanPham(productCost,voucherCost,deliveryCost,productQuantity)));
//                        totalCost2.setText(String.valueOf(GiaSanPham(productCost,voucherCost,deliveryCost,productQuantity)));
//                    } else {
//                        productQuantity.setText("0");
//                        totalCost.setText(String.valueOf(GiaSanPham(productCost,voucherCost,deliveryCost,productQuantity)));
//                        totalCost2.setText(String.valueOf(GiaSanPham(productCost,voucherCost,deliveryCost,productQuantity)));
//                    }
//                }
//            }
//        });
//        tvminus.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (TextUtils.isDigitsOnly(productCost.getText().toString())) {
//                    String currentQuantity = productQuantity.getText().toString();
//
//                    if (TextUtils.isDigitsOnly(currentQuantity)) {
//                        int quantity = Integer.parseInt(currentQuantity);
//                        quantity--;
//                        if (quantity < 0) {
//                            quantity = 0;
//                        }
//                        productQuantity.setText(String.valueOf(quantity));
//                        totalCost.setText(String.valueOf(GiaSanPham(productCost,voucherCost,deliveryCost,productQuantity)));
//                        totalCost2.setText(String.valueOf(GiaSanPham(productCost,voucherCost,deliveryCost,productQuantity)));
//                    } else {
//                        productQuantity.setText("0");
//                        totalCost.setText(String.valueOf(GiaSanPham(productCost,voucherCost,deliveryCost,productQuantity)));
//                        totalCost2.setText(String.valueOf(GiaSanPham(productCost,voucherCost,deliveryCost,productQuantity)));
//                    }
//                }
//            }
//        });
    }

    private void AnhXa() {
        productTitle = findViewById(R.id.Product_title);
        productSeller = findViewById(R.id.Product_seller);
        productImage = findViewById(R.id.buy_product_image);
        DateOrder = findViewById(R.id.dateoforder);
        productCost = findViewById(R.id.product_cost);
        voucherCost = findViewById(R.id.voucher_cost);
        deliveryCost = findViewById(R.id.delivery_cost);
        totalCost = findViewById(R.id.total_cost);
        totalCost2 = findViewById(R.id.total_cost_2);
        buyProduct = findViewById(R.id.buy_button);
        tvplus = findViewById(R.id.tvplus);
        tvminus = findViewById(R.id.tvminus);
        productQuantity = findViewById(R.id.amount);
        backbutton = findViewById(R.id.back_button);
    }

    private int GiaSanPham(TextView productCost, TextView voucherCost, TextView deliveryCost, EditText productQuantity) {
        int product_cost = Integer.parseInt(productCost.getText().toString());
        int voucher_cost = Integer.parseInt(voucherCost.getText().toString());
        int delivery_cost = Integer.parseInt(deliveryCost.getText().toString());
        int quantity = Integer.parseInt(productQuantity.getText().toString());
        return product_cost * quantity - voucher_cost + delivery_cost;
    }

}