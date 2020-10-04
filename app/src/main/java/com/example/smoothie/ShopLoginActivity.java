package com.example.smoothie;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class ShopLoginActivity extends AppCompatActivity {

    TextView noShopAcc;
    EditText shopEmail, shopPwd;
    Button loginBtn;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_login);

        noShopAcc = findViewById(R.id.txtNoAccShop);
        shopEmail = findViewById(R.id.txtLogEmail);
        shopPwd = findViewById(R.id.txtLogPassword);
        loginBtn = findViewById(R.id.btnLoginShopLabel);
        fAuth = FirebaseAuth.getInstance();

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = shopEmail.getText().toString();
                String password = shopPwd.getText().toString();

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

                fAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(ShopLoginActivity.this, "Logged in successfully", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                        }else{
                            Toast.makeText(ShopLoginActivity.this, "Error!!" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        noShopAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               navigateRegister();
            }
        });
    }

    private void navigateRegister() {
        Intent i = new Intent(this,RegisterShopActivity.class);
        startActivity(i);
    }


}