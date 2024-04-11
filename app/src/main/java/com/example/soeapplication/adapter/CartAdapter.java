package com.example.soeapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.soeapplication.HelperClass.CartProductClass;
import com.example.soeapplication.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyViewHolder> {
    Context context;
    ArrayList<CartProductClass> cartProductList;

    public CartAdapter(Context context, ArrayList<CartProductClass> cartProductList) {
        this.context = context;
        this.cartProductList = cartProductList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_cart_product, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        CartProductClass cartProduct = cartProductList.get(position);

        Glide.with(context).load(cartProduct.getImageUrl()).into(holder.buy_product_image);
        holder.product_title.setText(cartProduct.getName());
        cartProduct.getUsernameFromUserUid(cartProduct.getUseruid(), new CartProductClass.OnUsernameListener() {
            @Override
            public void onUsernameReceived(String username) {
                holder.product_seller.setText(username);
            }
        });
        holder.tvplus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int quantity = Integer.parseInt(holder.amount.getText().toString());
                if(quantity <= 99) {
                    quantity++;
                    holder.amount.setText(String.valueOf(quantity));
                    UpdateProductQuantity(cartProduct.getProduct_id(), quantity);
                }
            }
        });
        holder.tvminus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int quantity = Integer.parseInt(holder.amount.getText().toString());
                if (quantity > 1) {
                    quantity--;
                    holder.amount.setText(String.valueOf(quantity));
                    UpdateProductQuantity(cartProduct.getProduct_id(), quantity);
                }
            }
        });
        holder.product_cost.setText(cartProduct.getCost());
        holder.date_of_order.setText(cartProduct.getDate_of_order());
        holder.amount.setText(cartProduct.getProduct_quantity());
    }
    private void UpdateProductQuantity(String product_id, int quantity){
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser mUser = mAuth.getCurrentUser();
        FirebaseDatabase cart_database = FirebaseDatabase.getInstance();
        DatabaseReference cart_databaseReference = cart_database.getReference("cart");
        if(mUser != null) {
            cart_databaseReference.child(mUser.getUid()).child(product_id).child("product_quantity").setValue(String.valueOf(quantity));
        }
    }

    @Override
    public int getItemCount() {
        if(cartProductList != null){
            return cartProductList.size();
        }
        return 0;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView buy_product_image;
        TextView product_title, product_seller,product_cost, date_of_order, tvplus, tvminus;
        EditText amount;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            buy_product_image = itemView.findViewById(R.id.buy_product_image);
            product_title = itemView.findViewById(R.id.Product_title);
            product_seller = itemView.findViewById(R.id.Product_seller);
            product_cost = itemView.findViewById(R.id.buy_product_cost);
            date_of_order = itemView.findViewById(R.id.dateoforder);
            tvplus = itemView.findViewById(R.id.tvplus);
            tvminus = itemView.findViewById(R.id.tvminus);
            amount = itemView.findViewById(R.id.amount);
        }
    }
}
