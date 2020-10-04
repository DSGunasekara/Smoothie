package com.example.smoothie;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class RegisterShopActivity extends AppCompatActivity {

    EditText shopName, ownerName, shopEmail, shopPwd, shopPhone, shopLocation;
    Button shopRegBtn;
    TextView shopLoginBtn;
    FirebaseAuth fAuth;
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_shop);

        shopName = findViewById(R.id.txtShopName);
        shopEmail = findViewById(R.id.txtShopEmail);
        ownerName = findViewById(R.id.txtOwnerName);
        shopPwd = findViewById(R.id.txtShopPwd);
        shopPhone = findViewById(R.id.txtConNo);
        shopLocation = findViewById(R.id.txtLocation);
        shopRegBtn = findViewById(R.id.btnShopReg);
        shopLoginBtn = findViewById(R.id.btnLoginShopLabel);

        fAuth = FirebaseAuth.getInstance();
        final FirebaseFirestore fStore;
        final String shopID;

        if (fAuth.getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(), HomeActivity.class));
            finish();
        }

        shopLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),ShopLoginActivity.class));

            }
        });

        shopRegBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = shopEmail.getText().toString();
                String password = shopPwd.getText().toString();
                String phone = shopPhone.getText().toString();

                if (TextUtils.isEmpty(email)) {
                    shopEmail.setError("Email is required");
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    shopPwd.setError("Enter the password");
                    return;
                }

                if (password.length() < 8) {
                    shopPwd.setError("Password should have minimum 8 characters");
                    return;
                }

                if (phone.length() < 10) {
                    shopPhone.setError("Contact number should have 10 numbers");
                    return;
                } else if (phone.length() > 10) {
                    shopPhone.setError("Contact number cannot have more than 10 numbers");
                    return;
                }

                fAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(RegisterShopActivity.this, "Account created successfully", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                        } else {
                            Toast.makeText(RegisterShopActivity.this, "Error!!" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                    //shopID = fAuth.getCurrentUser().getUid(); //retrieve shopID
                    //DocumentReference documentReference = fStore.collection("Shops").document(shopID);
                    //Map<String, Object> shop = new HashMap<>();
                    //user.put("name", name);

                });


            }


        });
    }}