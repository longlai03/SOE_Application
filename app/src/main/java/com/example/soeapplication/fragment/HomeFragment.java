package com.example.soeapplication.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.soeapplication.ProductClass;
import com.example.soeapplication.R;
import com.example.soeapplication.activity.AddProduct;
import com.example.soeapplication.adapter.ProductAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    private RecyclerView product_recycleview;
    private ArrayList<ProductClass> product_list;
    private ProductAdapter productAdapter;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference product_databaseReference;
    private FloatingActionButton addProductButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        AnhXa(view);
        product_list = new ArrayList<>();
        productAdapter = new ProductAdapter(getActivity(), product_list);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        product_recycleview.setLayoutManager(gridLayoutManager);
        product_recycleview.setAdapter(productAdapter);

        addProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), AddProduct.class);
                startActivity(i);
            }
        });
        getProduct_list();

        return view;
    }

    private void getProduct_list() {
        //Them danh sach vao day
        product_databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(product_list != null){
                    product_list.clear();
                }
                for(DataSnapshot userSnapshot: snapshot.getChildren()) {
                    for (DataSnapshot productSnapshot : userSnapshot.getChildren()) {
                        ProductClass product = productSnapshot.getValue(ProductClass.class);
                        product_list.add(product);
                    }
                    productAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("FirebaseError", "Error reading data: " + error.getMessage());
            }
        });
    }

    private void AnhXa(View view) {
        product_recycleview = view.findViewById(R.id.ProductListRecycleView);
        addProductButton = view.findViewById(R.id.fb_addProduct);
        firebaseDatabase = FirebaseDatabase.getInstance();
        product_databaseReference = firebaseDatabase.getReference("product");
    }
}