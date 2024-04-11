//package com.example.soeapplication.adapter;
//
//import android.content.Context;
//import android.content.Intent;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.EditText;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.bumptech.glide.Glide;
//import com.example.soeapplication.ProductClass;
//import com.example.soeapplication.R;
//import com.example.soeapplication.activity.ProductDetail;
//
//import java.io.Serializable;
//import java.util.ArrayList;
//
//public class BuyProductAdapter extends RecyclerView.Adapter<BuyProductAdapter.MyViewHolder> {
//    Context context;
//    ArrayList<ProductClass> productList;
//
//    public BuyProductAdapter(Context context, ArrayList<ProductClass> productList) {
//        this.context = context;
//        this.productList = productList;
//    }
//
//    @NonNull
//    @Override
//    public BuyProductAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View v = LayoutInflater.from(context).inflate(R.layout.item_buy_product, parent, false);
//        return new BuyProductAdapter.MyViewHolder(v);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
//
//    }
//
//    @Override
//    public int getItemCount() {
//        return 0;
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull ProductAdapter.MyViewHolder holder, int position) {
//        ProductClass product = productList.get(position);
//
//        Glide.with(context).load(product.getImageUrl()).into(holder.Product_image);
//        holder.Product_title.setText(product.getShortName());
//        holder.Product_cost.setText(product.getCost());
//        product.getUsernameFromUserUid(product.getUseruid(), new ProductClass.OnUsernameListener() {
//            @Override
//            public void onUsernameReceived(String username) {
//                holder.Product_user.setText(username);
//            }
//        });
//
//        holder.Product_image.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ProductClass clickProduct = productList.get(holder.getAdapterPosition());
//                Intent i = new Intent(context, ProductDetail.class);
//                i.putExtra("product", (Serializable) clickProduct);
//                context.startActivity(i);
//            }
//        });
//    }
//
//    public static class MyViewHolder extends RecyclerView.ViewHolder {
//        ImageView buy_product_image;
//        TextView product_title, product_seller,date_of_order;
//        EditText amount;
//        public MyViewHolder(@NonNull View itemView) {
//            super(itemView);
//            buy_product_image = itemView.findViewById(R.id.buy_product_image);
//            product_title = itemView.findViewById(R.id.product_title);
//            product_seller = itemView.findViewById(R.id.product_cost);
//            date_of_order = itemView.findViewById(R.id.dateoforder);
//        }
//    }
//}
//
