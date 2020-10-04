package com.example.smoothie.Model;

import java.util.ArrayList;

public class OrderReview {
    private ArrayList<Order> orders;
    private String userId;
    private String totalAmount;
    private boolean ready;

    public OrderReview(ArrayList<Order> orders, String userId, String totalAmount, boolean ready) {
        this.orders = orders;
        this.userId = userId;
        this.totalAmount = totalAmount;
        this.ready = ready;
    }

    public OrderReview(){

    }

    public ArrayList<Order> getOrders() {
        return orders;
    }

    public void setOrders(ArrayList<Order> orders) {
        this.orders = orders;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public boolean isReady() {
        return ready;
    }

    public void setReady(boolean ready) {
        this.ready = ready;
    }
}
