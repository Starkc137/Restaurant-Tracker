package com.example.origins.Customers.Models;


public class CustomerOrder {
    private int orderId;
    private String customerName;
    private int staffId;
    private String orderStatus, orderTime, staffName, restaurantName, customerRating;
    ;

    public CustomerOrder(int orderId, String customerName, int staffId, String orderStatus, String orderTime, String staffName, String restaurantName, String customerRating) {
        this.orderId = orderId;
        this.customerName = customerName;
        this.staffId = staffId;
        this.orderStatus = orderStatus;
        this.orderTime = orderTime;
        this.staffName = staffName;
        this.restaurantName = restaurantName;
        this.customerRating = customerRating;
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

    public String getCustomerRating() {
        return customerRating;
    }
}

