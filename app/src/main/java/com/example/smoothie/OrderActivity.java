package com.example.smoothie;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.smoothie.Model.Order;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Document;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class OrderActivity extends AppCompatActivity {

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
//    private Order order;
    private FirebaseFirestore fStore;
    private FirebaseAuth fAuth;
    private String userId;
    public static final String TAG = "TAG";

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

        fStore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();

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
                if(qty != 1){
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
//                order = new Order(getIntent().getStringExtra("name"), tempTot, Integer.parseInt(getIntent().getStringExtra("price")), qty);
//                Intent intent = new Intent(OrderActivity.this, CartActivity.class);
//                intent.putExtra("orderList", order);
//                startActivity(intent);


                userId = fAuth.getCurrentUser().getUid(); //to retrieve uid of the current user
//                CollectionReference documentReference = fStore.collection("tempOrder");
                Map<String, Object> order = new HashMap<>();
                order.put("userId", userId);
                order.put("name", getIntent().getStringExtra("name"));
                order.put("price", getIntent().getStringExtra("price"));
                order.put("qty", qty);
                order.put("tempTotal",tempTot);

                //insert data into cloud database
                fStore.collection("tempOrder").add(order).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot written with ID: " + documentReference.getId());
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                        }
                    });

            }
        });
    }
}