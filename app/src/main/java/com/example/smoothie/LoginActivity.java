package com.example.smoothie;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {


    TextView txtAccountNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txtAccountNav = findViewById(R.id.txtIDontHaveAccount);

        txtAccountNav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateRegister();
            }
        });

    }

    public void navigateRegister(){
        Intent intent = new Intent(this,RegisterUserActivity.class);
        startActivity(intent);
    }
}