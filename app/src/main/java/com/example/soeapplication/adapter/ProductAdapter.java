package com.example.soeapplication.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.soeapplication.ProductClass;
import com.example.soeapplication.R;
import com.example.soeapplication.activity.ProductDetail;

import java.io.Serializable;
import java.util.ArrayList;

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
        TextView Product_title, Product_info, Product_cost, Product_user;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            Product_image = itemView.findViewById(R.id.product_imageView);
            Product_title = itemView.findViewById(R.id.product_title);
            Product_user = itemView.findViewById(R.id.product_user);
            Product_info = itemView.findViewById(R.id.product_info);
            Product_cost = itemView.findViewById(R.id.product_cost);
        }
    }
}
