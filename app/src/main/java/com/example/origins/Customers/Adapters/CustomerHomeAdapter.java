package com.example.origins.Customers.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.origins.R;
import com.example.origins.Staff.Order;

import java.util.List;

public class CustomerHomeAdapter extends RecyclerView.Adapter<CustomerHomeAdapter.OrderViewHolder> {

    private Context context;
    private List<Order> orderList;

    public CustomerHomeAdapter(Context context, List<Order> orderList) {
        this.context = context;
        this.orderList = orderList;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.customer_order, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        Order order = orderList.get(position);
        holder.textViewCustomerName.setText(": " + order.getCustomerName());
        holder.textViewStatus.setText("Status: " + order.getOrderStatus());
        holder.textViewRestaurantName.setText("Restaurant: " + order.getRestaurantName());

        // Set click listener for thumbs up button
        holder.textViewThumbsUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Perform thumbs up action
                Toast.makeText(context, "Thumbs Up clicked for Order ID: " + order.getOrderId(), Toast.LENGTH_SHORT).show();
            }
        });

        // Set click listener for thumbs down button
        holder.textViewThumbsDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Perform thumbs down action
                Toast.makeText(context, "Thumbs Down clicked for Order ID: " + order.getOrderId(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public class OrderViewHolder extends RecyclerView.ViewHolder {
        TextView textViewOrderId;
        TextView textViewStaffName, textViewCustomerName;
        TextView textViewOrderTime;
        TextView textViewStatus;
        TextView textViewRestaurantName;
        TextView textViewThumbsUp;
        TextView textViewThumbsDown;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewCustomerName = itemView.findViewById(R.id.txtCustomerName);
            textViewStatus = itemView.findViewById(R.id.txtOrderStatus);
            textViewRestaurantName = itemView.findViewById(R.id.txtResName);

        }
    }
}
