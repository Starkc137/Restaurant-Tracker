package com.example.origins.Customers.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.origins.Customers.Adapters.CustomerHomeAdapter;
import com.example.origins.Customers.Models.CustomerHomeModel;
import com.example.origins.R;

import java.util.ArrayList;
import java.util.List;

public class CustomerHomeFragment extends Fragment {

    RecyclerView Homeview;
    List<CustomerHomeModel> customerHomeModelList;
    CustomerHomeAdapter customerHomeAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_customer_cart, container, false);
        Homeview = root.findViewById(R.id.vertical_view);

        customerHomeModelList = new ArrayList<>();
        customerHomeModelList.add(new CustomerHomeModel(R.drawable.kfc, "KFC"));
        customerHomeModelList.add(new CustomerHomeModel(R.drawable.debonairs, "Debonairs"));
        customerHomeModelList.add(new CustomerHomeModel(R.drawable.nandos, "Nandos"));
        customerHomeModelList.add(new CustomerHomeModel(R.drawable.mcdonalds2, "McDonalds"));
        customerHomeModelList.add(new CustomerHomeModel(R.drawable.chicken_licken, "Chicken Licken"));
        customerHomeModelList.add(new CustomerHomeModel(R.drawable.steers, "Steers"));
        customerHomeModelList.add(new CustomerHomeModel(R.drawable.fishaways, "Fishaways"));

        customerHomeAdapter = new CustomerHomeAdapter(getActivity(), customerHomeModelList);
        Homeview.setAdapter(customerHomeAdapter);
        Homeview.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        Homeview.setHasFixedSize(true);
        Homeview.setNestedScrollingEnabled(false);

        return root;
    }


}
