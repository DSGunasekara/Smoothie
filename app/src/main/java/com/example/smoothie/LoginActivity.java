package com.example.smoothie;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smoothie.Model.Users;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {
//not working kindly check.......
    //**
        //**
            //**
                //**
                    //**
                        //**
    EditText login_contact, login_password;
    TextView txtAccountNav;
    Button btnLogin;
    ProgressDialog loadingBar;

    private String parentDbName = "Users";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txtAccountNav = findViewById(R.id.txtIDontHaveAccount);
        login_contact = findViewById(R.id.login_contact);
        login_password = findViewById(R.id.login_password);

        btnLogin = findViewById(R.id.btnLogin);

        loadingBar = new ProgressDialog(this);

        txtAccountNav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateRegister();
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });

    }

    private void loginUser() {
        String contact = login_contact.getText().toString();
        String password = login_password.getText().toString();

        if(TextUtils.isEmpty(contact)){
            Toast.makeText(this, "Please Enter your phone number",Toast.LENGTH_LONG).show();
        }else if(TextUtils.isEmpty(password)){
            Toast.makeText(this, "Please Enter your password",Toast.LENGTH_LONG).show();
        }else{
            loadingBar.setTitle("Login Account");
            loadingBar.setMessage("Please wait, while we are checking the account");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();

            AllowAccessToAccount(contact, password);
        }
    }

    private void AllowAccessToAccount(final String contact, String password) {
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(parentDbName).child(contact).exists()){
                    Users usersData = dataSnapshot.child(parentDbName).child(contact).getValue(Users.class);

                    if(usersData.getContact().equals(login_contact)){
                        if(usersData.getPassword().equals(login_password)){
                            Toast.makeText(LoginActivity.this, "Logged in Successfully", Toast.LENGTH_SHORT).show();

                            Intent intent =  new Intent(LoginActivity.this, HomeActivity.class);
                            startActivity(intent);
                            loadingBar.dismiss();

                        }else{
                            loadingBar.dismiss();
                            Toast.makeText(LoginActivity.this, "Password is incorrect", Toast.LENGTH_SHORT).show();
                        }
                    }
                }else{
                    Toast.makeText(LoginActivity.this, "Account with this "+contact+ " number do not exists", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                    Toast.makeText(LoginActivity.this, "You need to create an account", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void navigateRegister(){  
        Intent intent = new Intent(this,RegisterUserActivity.class);
        startActivity(intent);
    }
}