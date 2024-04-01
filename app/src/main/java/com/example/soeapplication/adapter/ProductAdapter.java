package com.example.soeapplication.adapter;

import android.content.Context;
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
        if(product == null){
            return;
        }
        Glide.with(context).load(product.getImageUrl()).into(holder.Product_image);
        holder.Product_title.setText(product.getName());
        holder.Product_info.setText(product.getInfomation());
        holder.Product_cost.setText(product.getCost());
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
        TextView Product_title, Product_info, Product_cost;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            Product_image = itemView.findViewById(R.id.product_imageView);
            Product_title = itemView.findViewById(R.id.product_title);
            Product_info = itemView.findViewById(R.id.product_info);
            Product_cost = itemView.findViewById(R.id.product_cost);
        }
    }
}
