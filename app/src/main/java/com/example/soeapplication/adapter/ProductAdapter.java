package com.example.soeapplication.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.soeapplication.CartProductClass;
import com.example.soeapplication.ProductClass;
import com.example.soeapplication.R;
import com.example.soeapplication.activity.ProductDetail;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.MyViewHolder> {
    Context context;
    ArrayList<ProductClass> productList;

    public ProductAdapter(Context context, ArrayList<ProductClass> productList) {
        this.context = context;
        this.productList = productList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_product, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ProductClass product = productList.get(position);

        Glide.with(context).load(product.getImageUrl()).into(holder.Product_image);
        holder.Product_title.setText(product.getShortName());
        holder.Product_info.setText(product.getShortInfomation());
        holder.Product_cost.setText(product.getCost());
        product.getUsernameFromUserUid(product.getUseruid(), new ProductClass.OnUsernameListener() {
            @Override
            public void onUsernameReceived(String username) {
                holder.Product_user.setText(username);
            }
        });

        holder.Product_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProductClass clickProduct = productList.get(holder.getAdapterPosition());
                Intent i = new Intent(context, ProductDetail.class);
                i.putExtra("product", (Serializable) clickProduct);
                context.startActivity(i);
            }
        });
        holder.addToCart_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth mAuth = FirebaseAuth.getInstance();
                FirebaseUser mUser = mAuth.getCurrentUser();
                ProductClass clickedProduct = productList.get(holder.getAdapterPosition());
                CartProductClass cartProduct = new CartProductClass();
                cartProduct.setUseruid(mUser.getUid());
                cartProduct.setName(clickedProduct.getName());
                cartProduct.setCost(clickedProduct.getCost());
                cartProduct.setImageUrl(clickedProduct.getImageUrl());
                cartProduct.setProduct_id(clickedProduct.getProduct_id());
                cartProduct.setProduct_quantity("1");

                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                String currentDate = sdf.format(new Date());
                cartProduct.setDate_of_order(currentDate);

                FirebaseDatabase cart_firebaseDatabase = FirebaseDatabase.getInstance();
                DatabaseReference cart_databaseReference = cart_firebaseDatabase.getReference("cart").child(mUser.getUid()).child(cartProduct.getProduct_id());

                cart_databaseReference.setValue(cartProduct).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(context,"Sản phẩm đã được thêm vào giỏ hàng", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context,"Lỗi khi thêm sản phẩm vào giỏ hàng", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        if(productList != null){
            return productList.size();
        }
        return 0;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView Product_image;
        ImageButton addToCart_button;
        TextView Product_title, Product_info, Product_cost, Product_user;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            Product_image = itemView.findViewById(R.id.product_imageView);
            Product_title = itemView.findViewById(R.id.product_title);
            Product_user = itemView.findViewById(R.id.product_user);
            Product_info = itemView.findViewById(R.id.product_info);
            Product_cost = itemView.findViewById(R.id.product_cost);
            addToCart_button = itemView.findViewById(R.id.addToCart_button);
        }
    }
}
