package com.example.origins.Staff.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.origins.LoginActivity;
import com.example.origins.R;

import java.util.HashMap;
import java.util.Map;


public class StaffAccountFragment extends Fragment {

    private String email;
    private TextView emailtxt, nametxt, ratingstxt;
    private Button signout;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_staff_account, container, false);

        nametxt = rootView.findViewById(R.id.accountNameS);
        emailtxt = rootView.findViewById(R.id.accountEmailS);
        ratingstxt = rootView.findViewById(R.id.staffRatings);
        signout = rootView.findViewById(R.id.signOutS);


        email = getActivity().getIntent().getStringExtra("email");

        emailtxt.setText("Account: " + email);

        getRatings(email);
        getUsername(email);

        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity().getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });

        return rootView;
    }

    private void getRatings(String email) {
        String url = "https://lamp.ms.wits.ac.za/~s2451244/assignment/fetch_avarage_rating.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        ratingstxt.setText("Avarage Rating: " + response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("email", email);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        requestQueue.add(stringRequest);
    }

    private void getUsername(String email) {
        String url = "https://lamp.ms.wits.ac.za/~s2451244/fetch_username.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        nametxt.setText("Staff Name: " + response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("email", email);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        requestQueue.add(stringRequest);
    }
}