package com.example.origins.Staff.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.origins.R;
import com.example.origins.Staff.Order;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {

    private List<Order> orderList;
    private OrderClickListener orderClickListener;

    public OrderAdapter(List<Order> orderList, OrderClickListener orderClickListener) {
        this.orderList = orderList;
        this.orderClickListener = orderClickListener;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_item, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        Order order = orderList.get(position);
        holder.txtOrderId.setText("Order Number: " + order.getOrderId());
        holder.txtCustomerName.setText("Customer name: " + order.getCustomerName());
        holder.txtStaffId.setText("Order Creator: " + order.getStaffName());
        holder.txtResName.setText("Restaurant: " + order.getRestaurantName());
        holder.txtOrderStatus.setText("Order Status: " + order.getOrderStatus());
        holder.txtOrderTime.setText("Time: " + order.getOrderTime());


        // Set click listener for the card view
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orderClickListener.onOrderClick(order);
            }
        });
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


        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardView);
            txtOrderId = itemView.findViewById(R.id.txtOrderId);
            txtCustomerName = itemView.findViewById(R.id.txtCustomerName);
            txtStaffId = itemView.findViewById(R.id.txtStaffId);
            txtResName = itemView.findViewById(R.id.txtResName);
            txtOrderStatus = itemView.findViewById(R.id.txtOrderStatus);
            txtOrderTime = itemView.findViewById(R.id.txtOrderTime);

        }
    }
}
