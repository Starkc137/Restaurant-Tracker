package com.example.origins.Staff.Fragments;

import static com.example.origins.LoginActivity.EMAIL_REGEX;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.origins.R;

import java.util.HashMap;
import java.util.Map;

public class StaffHomeFragment extends Fragment {
    private final String URL = "https://lamp.ms.wits.ac.za/~s2451244/add_order.php";
    String nameCus, emailCus, nameStaff, nameRestaurant, email;
    private Button createOrder;
    private EditText customerEmail, staffName;
    private Spinner restaurantName;
    private ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_staff_home, container, false);

        // Find the button in the layout
        createOrder = view.findViewById(R.id.create_order);
        customerEmail = view.findViewById(R.id.customerEmail);
        staffName = view.findViewById(R.id.staffMemberName);
        progressBar = view.findViewById(R.id.progressBarr);
        restaurantName = view.findViewById(R.id.restaurant_name_spinner);


        //Code to set spinner color
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                getActivity().getApplicationContext(), R.array.Restaurants, R.layout.spinner_item_layout);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item_layout);
        restaurantName.setAdapter(adapter);


        email = getActivity().getIntent().getStringExtra("email");
        getUsername(email);


        createOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emailCus = customerEmail.getText().toString();
                nameStaff = staffName.getText().toString();
                nameRestaurant = restaurantName.getSelectedItem().toString();

                if (nameStaff.isEmpty()) {
                    staffName.setError("Enter Staff Name");
                    return;
                }

                if (!nameStaff.equals(nameCus)) {
                    staffName.setError("Enter your own staff Name");
                    return;
                }

                if (emailCus.isEmpty() || !emailCus.matches(EMAIL_REGEX)) {
                    customerEmail.setError("Please input valid email");
                    return;
                }

                if (nameRestaurant.equals("None")) {
                    Toast.makeText(getActivity().getApplicationContext(), "Select valid Restaurant", Toast.LENGTH_SHORT).show();
                } else {
                    addOrder();
                }

            }
        });

        return view;
    }

    private void addOrder() {
        progressBar.setVisibility(View.VISIBLE);
        // Get user inputs
        emailCus = customerEmail.getText().toString();
        nameStaff = staffName.getText().toString();
        nameRestaurant = restaurantName.getSelectedItem().toString();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressBar.setVisibility(View.GONE);
                if (response.equals("Success")) {
                    // Transition to the OrdersFragment
                    Toast.makeText(getActivity().getApplicationContext(), "Order Created Successfully", Toast.LENGTH_SHORT).show();
                } else if (response.equals("Failure")) {
                    Toast.makeText(getActivity().getApplicationContext(), "Error: Query Failed", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //error message
                Toast.makeText(getActivity().getApplicationContext(), error.toString().trim(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> data = new HashMap<>();
                data.put("email", emailCus);
                data.put("restaurant_name", nameRestaurant);
                data.put("staff_name", nameStaff);
                return data;
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
                        nameCus = response;
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
