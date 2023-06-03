package com.example.origins.Customers.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.origins.R;
import com.example.origins.Staff.Order;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomerOrderAdapter extends RecyclerView.Adapter<CustomerOrderAdapter.OrderViewHolder> {

    private List<Order> orderList;
    private OrderClickListener orderClickListener;
    private RequestQueue requestQueue;
    private Context context;

    public CustomerOrderAdapter(Context context, List<Order> orderList, OrderClickListener orderClickListener) {
        this.context = context;
        this.orderList = orderList;
        this.orderClickListener = orderClickListener;
        requestQueue = Volley.newRequestQueue(context.getApplicationContext());
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.customer_order, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        Order order = orderList.get(position);

        holder.txtCustomerName.setText("Order no: " + order.getOrderId() + " for " + order.getCustomerName());
        holder.txtResName.setText("Restaurant: " + order.getRestaurantName());
        holder.txtOrderStatus.setText("Status: " + order.getOrderStatus());

        if (order.isRatingClicked()) {
            holder.imgThumbsUp.setEnabled(false);
            holder.imgThumbsDown.setEnabled(false);
        } else {
            holder.imgThumbsUp.setEnabled(true);
            holder.imgThumbsDown.setEnabled(true);
        }

        // Set click listeners for thumbs up and thumbs down icons
        holder.imgThumbsUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateRating(order.getOrderId(), "thumbs_up");
                holder.imgThumbsUp.setImageResource(R.drawable.thumbs_up_clicked);
                holder.imgThumbsDown.setImageResource(R.drawable.ic_thumb_down);
                order.setRatingClicked(true);
            }
        });

        holder.imgThumbsDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateRating(order.getOrderId(), "thumbs_down");
                holder.imgThumbsDown.setImageResource(R.drawable.thumbs_down_clicked);
                holder.imgThumbsUp.setImageResource(R.drawable.ic_thumb_up);
                order.setRatingClicked(true);
            }
        });

        // Set click listener for the card view
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orderClickListener.onOrderClick(order);
            }
        });
    }

    private void updateRating(int orderId, String buttonType) {
        String url = "https://lamp.ms.wits.ac.za/~s2451244/assignment/update_rating.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

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
                params.put("order_id", String.valueOf(orderId));
                params.put("button_type", buttonType);
                return params;
            }
        };

        requestQueue.add(stringRequest);
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public interface OrderClickListener {
        void onOrderClick(Order order);
    }

    public class OrderViewHolder extends RecyclerView.ViewHolder {
        public CardView cardView;
        private TextView txtOrderId, txtCustomerName, txtStaffId, txtOrderStatus, txtOrderTime, txtResName;
        private ImageView imgThumbsUp, imgThumbsDown;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardView);
            txtOrderId = itemView.findViewById(R.id.txtOrderId);
            txtCustomerName = itemView.findViewById(R.id.txtCustomerName);
            txtStaffId = itemView.findViewById(R.id.txtStaffId);
            txtResName = itemView.findViewById(R.id.txtResName);
            txtOrderStatus = itemView.findViewById(R.id.txtOrderStatus);
            txtOrderTime = itemView.findViewById(R.id.txtOrderTime);
            imgThumbsUp = itemView.findViewById(R.id.imgThumbsUp);
            imgThumbsDown = itemView.findViewById(R.id.imgThumbsDown);
        }
    }
}
