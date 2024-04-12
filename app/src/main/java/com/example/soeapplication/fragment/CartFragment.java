package com.example.soeapplication.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.soeapplication.HelperClass.CartProductClass;
import com.example.soeapplication.HelperClass.UserClass;
import com.example.soeapplication.R;
import com.example.soeapplication.activity.PaymentDialog;
import com.example.soeapplication.adapter.CartAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CartFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CartFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CartFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CartFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CartFragment newInstance(String param1, String param2) {
        CartFragment fragment = new CartFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    private RecyclerView cartRecycleview;
    private CartAdapter cartAdapter;
    private ArrayList<CartProductClass> cartProduct_list;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private FirebaseDatabase cart_database, user_database;
    private DatabaseReference cart_databaseReference, user_databaseReference;
    private TextView total_cost, address_text;
    private Button checkout_button;
    private CircleImageView avatar_button;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        AnhXa(view);
        cartProduct_list = new ArrayList<>();
        cartAdapter = new CartAdapter(getActivity(), cartProduct_list);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 1);
        cartRecycleview.setLayoutManager(gridLayoutManager);
        cartRecycleview.setAdapter(cartAdapter);
        getProduct_list();
        getAddress();

        avatar_button = view.findViewById(R.id.avatar_button_3);
        avatar_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomNavigationView bottomNavigationView = getActivity().findViewById(R.id.bottom_navigation);
                int userFragmentPosition = 3;
                bottomNavigationView.setSelectedItemId(bottomNavigationView.getMenu().getItem(userFragmentPosition).getItemId());
            }
        });
        checkout_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth = FirebaseAuth.getInstance();
                mUser = mAuth.getCurrentUser();
                if(mUser != null && TextUtils.isDigitsOnly(total_cost.getText().toString())) {
                    if(Integer.parseInt(total_cost.getText().toString()) > 0) {
                        String totalPrice = total_cost.getText().toString();
                        PaymentDialog paymentDialog = new PaymentDialog(getActivity(), totalPrice);
                        paymentDialog.show();
                    } else {
                        Toast.makeText(getActivity(),"Hãy thêm sản phẩm vào giỏ hàng", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getActivity(),"Yêu cầu đăng nhập", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }

    private void AnhXa(View view) {
        cartRecycleview = view.findViewById(R.id.cart_recyclerView);
        total_cost = view.findViewById(R.id.total_product_cost);
        address_text = view.findViewById(R.id.address_text);
        checkout_button = view.findViewById(R.id.checkout_button);
    }

    private void getProduct_list() {
        //Lay tai khoan
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        if(mUser != null) {
            cart_database = FirebaseDatabase.getInstance();
            cart_databaseReference = cart_database.getReference("cart").child(mUser.getUid());
            //Them danh sach vao day
            cart_databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (cartProduct_list != null) {
                        cartProduct_list.clear();
                    }
                    for (DataSnapshot productSnapshot : snapshot.getChildren()) {
                        CartProductClass cartProduct = productSnapshot.getValue(CartProductClass.class);
                        cartProduct_list.add(cartProduct);
                    }
                    cartAdapter.notifyDataSetChanged();
                    TotalPrice(total_cost);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Log.e("FirebaseError", "Error reading data: " + error.getMessage());
                }
            });
        } else {
            cartProduct_list.clear();
            Toast.makeText(getActivity(),"Yêu cầu đăng nhập",Toast.LENGTH_SHORT).show();
        }
    }
    private void getAddress(){
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        if(mUser != null){
            user_database = FirebaseDatabase.getInstance();
            user_databaseReference = user_database.getReference("user").child(mUser.getUid());
            user_databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.exists()){
                        UserClass user = snapshot.getValue(UserClass.class);
                        address_text.setText(user.getAddress());
                    } else {
                        address_text.setText("Người dùng chưa cập nhật địa chỉ");
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        } else {
            address_text.setText("Người dùng chưa đăng ");
        }
    }

    private void TotalPrice(TextView total_cost) {
        int total = 0;
        for (CartProductClass cartProduct : cartProduct_list) {
            total += Integer.parseInt(cartProduct.getCost()) * Integer.parseInt(cartProduct.getProduct_quantity());
        }
        total_cost.setText(String.valueOf(total));
    }

    @Override
    public void onResume() {
        super.onResume();
        getProduct_list();
        getAddress();
    }
}