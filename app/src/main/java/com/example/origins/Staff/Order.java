package com.example.origins.Staff;

public class Order {
    private final int orderId;
    private final String orderStatus;
    private final String orderTime;
    private final String staffName;
    private final String restaurantName;
    private final String customerName;
    private final int staffId;
    private boolean ratingClicked;

    public Order(int orderId, String customerName, int staffId, String orderStatus, String orderTime, String staffName, String restaurantName) {
        this.orderId = orderId;
        this.customerName = customerName;
        this.staffId = staffId;
        this.orderStatus = orderStatus;
        this.orderTime = orderTime;
        this.staffName = staffName;
        this.restaurantName = restaurantName;
        this.ratingClicked = false; // Initialize the ratingClicked flag to false
    }

    public int getOrderId() {
        return orderId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public int getStaffId() {
        return staffId;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public String getStaffName() {
        return staffName;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public boolean isRatingClicked() {
        return ratingClicked;
    }

    public void setRatingClicked(boolean ratingClicked) {
        this.ratingClicked = ratingClicked;
    }
}
