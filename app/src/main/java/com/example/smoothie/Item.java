package com.example.smoothie;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.WriteBatch;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.rey.material.widget.ImageView;
import com.squareup.picasso.Picasso;

import java.util.List;

public class Item extends AppCompatActivity {

    private static final String TAG = "TAG";
    private TextView name;
    private TextView price;
    private TextView description;
//    private ImageView image;

    private Button btnDelete, btnUpdate;

    DatabaseReference dbRef;

    private String Name;

    private FirebaseStorage storage;
    private StorageReference storageReference;

    Product prd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);
        name = findViewById(R.id.juiceName);
        price = findViewById(R.id.juicePrice);
//        image = findViewById(R.id.product_image);
        description = findViewById(R.id.juiceDescription);
        btnDelete = findViewById(R.id.btnDelete);
        btnUpdate = findViewById(R.id.btnUpdate);

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();



        prd = new Product();

        name.setText( getIntent().getStringExtra("name"));
        price.setText( getIntent().getStringExtra("price"));
        description.setText( getIntent().getStringExtra("description"));


        Name = name.getText().toString();
        Log.d(TAG, "onCreate: "+Name);
        //Delete user profile
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseFirestore.getInstance().collection("item").document(Name)
                        .delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(prd, "Data Deleted", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });




        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(v.getContext(),UpdateItem.class);
                intent.putExtra("name",name.getText().toString());
                intent.putExtra("price",price.getText().toString());
                intent.putExtra("description",description.getText().toString());
                startActivity(intent);

            }
        });



    }



    private void navigateUpdateItem() {
        Intent intents = new Intent(this, UpdateItem.class);
        startActivity(intents);
    }
}