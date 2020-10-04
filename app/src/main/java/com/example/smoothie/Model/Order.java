package com.example.smoothie.Model;


import java.io.Serializable;

public class Order implements Serializable {
    private int totAmount;
    private String name;
    private int price;
    private int qty;

    public int getTotAmount() {
        return totAmount;
    }

    public void setTotAmount(int totAmount) {
        this.totAmount = totAmount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public Order(){

    }

    public Order(String name, int totAmount, int price, int qty) {
        this.totAmount = totAmount;
        this.name = name;
        this.price = price;
        this.qty = qty;
    }
}
