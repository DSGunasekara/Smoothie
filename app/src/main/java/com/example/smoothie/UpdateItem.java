package com.example.smoothie;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;

import java.util.HashMap;
import java.util.Map;

public class UpdateItem extends AppCompatActivity {

    public static final String TAG = "TAG";
    EditText editName, editPrice, editDescription;
    ImageView EditProductImage;
    Button btnSave;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_item);

        Intent data = getIntent();
        final String name = data.getStringExtra("name");
        String price = data.getStringExtra("price");
        String description = data.getStringExtra("description");

        editName = findViewById(R.id.txtName);
        editPrice = findViewById(R.id.txtPrice);
        editDescription = findViewById(R.id.txtDescription);
        btnSave = findViewById(R.id.btn_save);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();





        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editName.getText().toString().isEmpty() || editPrice.getText().toString().isEmpty() || editDescription.getText().toString().isEmpty()) {
                    Toast.makeText(UpdateItem.this, "one or many fields are empty", Toast.LENGTH_SHORT).show();
                    return;
                }



                DocumentReference docRef = fStore.collection("item").document(name);

                Map<String, Object> edited = new HashMap<>();
                edited.put("name", editName.getText().toString());
                edited.put("price", editPrice.getText().toString());
                edited.put("description", editDescription.getText().toString());

                docRef.update(edited).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(UpdateItem.this, "Item updated", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                        finish();

                    }


                });


            }

        });

        editName.setText(name);
        editPrice.setText(price);
        editDescription.setText(description);

        Log.d(TAG,"onCreate: " +name + " " +price+ " " +description);

    }
}