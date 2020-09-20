package com.example.smoothie;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {


    Button btnSqueeze;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSqueeze = findViewById(R.id.btnSqueeze);

        btnSqueeze.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateLogin();

            }
        });
    }
    public void navigateLogin(){
        Intent intent = new Intent(this,LoginActivity.class);
        startActivity(intent);
    }
}