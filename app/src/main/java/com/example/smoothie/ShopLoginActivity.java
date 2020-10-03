package com.example.smoothie;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ShopLoginActivity extends AppCompatActivity {

    TextView noShopAcc;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_login);

        noShopAcc = findViewById(R.id.txtNoAccShop);

        noShopAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RegisterShop();
            }
        });
    }

    public void RegisterShop() {
        Intent i = new Intent(ShopLoginActivity.this, RegisterShopActivity.class);
        startActivity(i);
    }
}