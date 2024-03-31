package com.example.soeapplication.fragment;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.soeapplication.ProductClass;
import com.example.soeapplication.R;
import com.example.soeapplication.adapter.ProductAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        product_recycleview = view.findViewById(R.id.ProductListRecycleView);
        product_list = getProduct_list();
        productAdapter = new ProductAdapter(getActivity(), product_list);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        product_recycleview.setLayoutManager(gridLayoutManager);
        product_recycleview.setAdapter(productAdapter);

        return view;
    }

    private ArrayList<ProductClass> getProduct_list(){
        ArrayList<ProductClass> list = new ArrayList<>();
        list.add(new ProductClass("Kem", "Mon kem xua tan met moi sau chuoi ngay lam viec vat va","10000", "link"));
        list.add(new ProductClass("Kem", "Mon kem xua tan met moi sau chuoi ngay lam viec vat va","10000", "link"));

        list.add(new ProductClass("Kem", "Mon kem xua tan met moi sau chuoi ngay lam viec vat va","10000", "link"));
        list.add(new ProductClass("Kem", "Mon kem xua tan met moi sau chuoi ngay lam viec vat va","10000", "link"));

        list.add(new ProductClass("Kem", "Mon kem xua tan met moi sau chuoi ngay lam viec vat va","10000", "link"));
        list.add(new ProductClass("Kem", "Mon kem xua tan met moi sau chuoi ngay lam viec vat va","10000", "link"));

        return list;
    }
}