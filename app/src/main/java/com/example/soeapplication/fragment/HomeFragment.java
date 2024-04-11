package com.example.soeapplication.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.soeapplication.HelperClass.ProductClass;
import com.example.soeapplication.R;
import com.example.soeapplication.activity.AddProduct;
import com.example.soeapplication.adapter.ProductAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

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

    private EditText search_editText;
    private RecyclerView product_recycleview;
    private ArrayList<ProductClass> product_list;
    private ProductAdapter productAdapter;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference product_databaseReference;
    private FloatingActionButton addProductButton;
    private CircleImageView avatar_button;
    private TextView numberofProduct;

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
        getProduct_list();

        addProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), AddProduct.class);
                startActivity(i);
            }
        });
        search_editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String text = s.toString().trim();
                if (!TextUtils.isEmpty(text)) {
                    SearchProduct(text);
//                    Log.e("this", "text: " + text);
                } else {
                    getProduct_list();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        avatar_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomNavigationView bottomNavigationView = getActivity().findViewById(R.id.bottom_navigation);
                int userFragmentPosition = 3;
                bottomNavigationView.setSelectedItemId(bottomNavigationView.getMenu().getItem(userFragmentPosition).getItemId());
            }
        });
        return view;
    }

    private void getProduct_list() {
        //Them danh sach vao day
        product_databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (product_list != null) {
                    product_list.clear();
                }
                for (DataSnapshot productSnapshot : snapshot.getChildren()) {
                    ProductClass product = productSnapshot.getValue(ProductClass.class);
                    product_list.add(product);
                    numberofProduct.setText(String.valueOf(product_list.size()));
                }
                productAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("FirebaseError", "Error reading data: " + error.getMessage());
            }
        });
    }

    private void SearchProduct(String text) {
        Query query = product_databaseReference.orderByChild("name").startAt(text).endAt(text.toLowerCase() + "\uf8ff");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                product_list.clear();
                for (DataSnapshot productSnapshot : snapshot.getChildren()) {
                    ProductClass filter_product = productSnapshot.getValue(ProductClass.class);
                    if (filter_product != null) {
                        String productNameLowerCase = filter_product.getLowerCaseName().toLowerCase();
                        if (productNameLowerCase.contains(text.toLowerCase())) {
                            product_list.add(filter_product);
                            Log.e("this", "Danh sach da duoc thay doi");
                        }
                    }
                }
                productAdapter.notifyDataSetChanged();
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
        search_editText = view.findViewById(R.id.search_editText);
        avatar_button = view.findViewById(R.id.avatar_button);
        numberofProduct = view.findViewById(R.id.numberofProduct);
    }
}