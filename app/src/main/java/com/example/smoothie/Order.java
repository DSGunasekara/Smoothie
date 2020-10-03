package com.example.smoothie;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Order extends AppCompatActivity {

    private TextView name;
    private TextView price;
    private TextView description;
    private TextView amount;
    private  TextView totAmount;
    private Button orderBtn;
    private Button addQty;
    private Button remQty;
    private int qty = 1;
    private int tempTot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        name = findViewById(R.id.juiceName);
        price = findViewById(R.id.juicePrice);
        description = findViewById(R.id.juiceDescription);
        orderBtn = findViewById(R.id.btnOrder);
        amount = findViewById(R.id.juiceAmount);
        totAmount = findViewById(R.id.juiceTotAmount);
        addQty = findViewById(R.id.btnAmountAdd);
        remQty = findViewById(R.id.btnAmountNeg);

        name.setText("Name: " + getIntent().getStringExtra("name"));
        price.setText("Price: " + getIntent().getStringExtra("price"));
        description.setText("Description: " + getIntent().getStringExtra("description"));
        amount.setText(Integer.toString(qty));

        tempTot = qty * Integer.parseInt(getIntent().getStringExtra("price"));
        totAmount.setText("Total Price: " + tempTot);

        addQty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                qty+=1;
                amount.setText(Integer.toString(qty));
                tempTot = qty * Integer.parseInt(getIntent().getStringExtra("price"));
                totAmount.setText("Total Price: " + tempTot);
            }
        });

        remQty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(qty != 0){
                    qty-=1;
                    amount.setText(Integer.toString(qty));
                    tempTot = qty * Integer.parseInt(getIntent().getStringExtra("price"));
                    totAmount.setText("Total Price: " + tempTot);
                }
            }
        });

        orderBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                orderBtn.setText("Added to cart");
            }
        });
    }
}