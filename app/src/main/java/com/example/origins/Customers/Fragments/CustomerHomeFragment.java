package com.example.origins.Customers.Fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.origins.Customers.Adapters.CustomerOrderAdapter;
import com.example.origins.R;
import com.example.origins.Staff.Order;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class CustomerHomeFragment extends Fragment implements CustomerOrderAdapter.OrderClickListener {

    private static final String PHP_URL = "https://lamp.ms.wits.ac.za/~s2451244/assignment/get_customer.php";
    private static final String PHP_UPDATE_URL = "https://lamp.ms.wits.ac.za/~s2451244/assignment/update_order.php";
    private RecyclerView recyclerView;
    private CustomerOrderAdapter orderAdapter;
    private List<Order> orderList;
    private boolean doubleBackToExitPressedOnce = false;
    private String email;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_customer_home, container, false);

        recyclerView = rootView.findViewById(R.id.recyclerView_customer);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        orderList = new ArrayList<>();
        orderAdapter = new CustomerOrderAdapter(getContext(), orderList, this);
        recyclerView.setAdapter(orderAdapter);

        email = getActivity().getIntent().getStringExtra("email");

        FetchOrdersTask fetchOrdersTask = new FetchOrdersTask(email);
        fetchOrdersTask.execute();

        return rootView;
    }

    @Override
    public void onOrderClick(Order order) {
        // Handle order click

    }

    private class FetchOrdersTask extends AsyncTask<Void, Void, List<Order>> {
        private String email;

        public FetchOrdersTask(String email) {
            this.email = email;
        }


        @Override
        protected List<Order> doInBackground(Void... voids) {
            List<Order> orders = new ArrayList<>();

            try {
                String urlWithParams = PHP_URL + "?email=" + email;
                URL url = new URL(urlWithParams);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");

                InputStream inputStream = connection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                StringBuilder response = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    response.append(line);
                }

                bufferedReader.close();
                inputStream.close();
                connection.disconnect();

                JSONArray jsonArray = new JSONArray(response.toString());
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    int orderId = jsonObject.getInt("order_id");
                    String customerName = jsonObject.getString("customer_name");
                    int staffId = jsonObject.getInt("staff_id");
                    String orderStatus = jsonObject.getString("status");
                    String orderTime = jsonObject.getString("order_time");
                    String staffName = jsonObject.getString("staff_name");
                    String restaurantName = jsonObject.getString("restaurant_name");
                    Order order = new Order(orderId, customerName, staffId, orderStatus, orderTime, staffName, restaurantName);
                    orders.add(order);
                }
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }

            return orders;
        }

        @Override
        protected void onPostExecute(List<Order> orders) {
            orderList.clear();
            orderList.addAll(orders);
            orderAdapter.notifyDataSetChanged();
        }
    }

    private class UpdateOrderTask extends AsyncTask<Void, Void, Boolean> {
        private int orderId;
        private String newStatus;

        public UpdateOrderTask(int orderId, String newStatus) {
            this.orderId = orderId;
            this.newStatus = newStatus;
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            try {
                URL url = new URL(PHP_UPDATE_URL + "?order_id=" + orderId + "&status=" + newStatus);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");

                int responseCode = connection.getResponseCode();

                connection.disconnect();

                return responseCode == HttpURLConnection.HTTP_OK;
            } catch (IOException e) {
                e.printStackTrace();
            }

            return false;
        }

        @Override
        protected void onPostExecute(Boolean success) {
            if (success) {
                Toast.makeText(getActivity(), "Order status updated", Toast.LENGTH_SHORT).show();
                FetchOrdersTask fetchOrdersTask = new FetchOrdersTask(email);
                fetchOrdersTask.execute();
            } else {
                Toast.makeText(getActivity(), "Failed to update order status", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private class UpdateRatingTask extends AsyncTask<Void, Void, Boolean> {
        private int orderId;
        private String newRating;

        public UpdateRatingTask(int orderId, String newRating) {
            this.orderId = orderId;
            this.newRating = newRating;
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            try {
                URL url = new URL(PHP_UPDATE_URL + "?order_id=" + orderId + "&customer_rating=" + newRating);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");

                int responseCode = connection.getResponseCode();

                connection.disconnect();

                return responseCode == HttpURLConnection.HTTP_OK;
            } catch (IOException e) {
                e.printStackTrace();
            }

            return false;
        }

        @Override
        protected void onPostExecute(Boolean success) {
            if (success) {
                Toast.makeText(getActivity(), "Customer rating updated", Toast.LENGTH_SHORT).show();
                FetchOrdersTask fetchOrdersTask = new FetchOrdersTask(email);
                fetchOrdersTask.execute();
            } else {
                Toast.makeText(getActivity(), "Failed to update customer rating", Toast.LENGTH_SHORT).show();
            }
        }
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Handle the back button press
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                    if (doubleBackToExitPressedOnce) {
                        // User clicked back button twice, exit the app
                        requireActivity().finish();
                    } else {
                        // Show a Toast message on the first click
                        doubleBackToExitPressedOnce = true;
                        Toast.makeText(requireContext(), "Press back again to exit", Toast.LENGTH_SHORT).show();
                        // Reset the flag after a certain duration (e.g., 2 seconds)
                        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                doubleBackToExitPressedOnce = false;
                            }
                        }, 2000);
                    }
                    return true;
                }
                return false;
            }
        });
    }
}
