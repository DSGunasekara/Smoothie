package com.example.smoothie;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.smoothie.Model.Order;

import java.util.ArrayList;
import java.util.List;

public class OrderListAdapter extends ArrayAdapter<Order> {
    private Context mContext;
    private int mResource;

    public OrderListAdapter(@NonNull Context context, int resource, @NonNull List<Order> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.mResource = resource;
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        int totAmount = getItem(position).getTotAmount();
        String name = getItem(position).getName();
        int price = getItem(position).getPrice();
        int qty = getItem(position).getQty();

        Order order = new Order(name, totAmount, price, qty);
//        LayoutInflater inflater = LayoutInflater.from(mContext);
//        LayoutInflater layoutInflater = (LayoutInflater)geta
        convertView = LayoutInflater.from(mContext).inflate(mResource, parent, false);

        TextView orderName = (TextView) convertView.findViewById(R.id.orderName);
        TextView orderPrice = (TextView)convertView.findViewById(R.id.orderPrice);
        TextView orderQty = (TextView)convertView.findViewById(R.id.orderQty);
        TextView orderAmount = (TextView)convertView.findViewById(R.id.orderAmount);

        orderName.setText(name);
        orderPrice.setText(price+"");
        orderQty.setText(qty+"");
        orderAmount.setText(totAmount+"");
        return convertView;
    }
}
