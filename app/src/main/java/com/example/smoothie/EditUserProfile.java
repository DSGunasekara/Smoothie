package com.example.smoothie;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class EditUserProfile extends AppCompatActivity {

    public static final String TAG = "TAG";

    EditText editName , editContact, editEmail;
    ImageView EditProfileImageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user_profile);

        Intent data = getIntent();
        String name = data.getStringExtra("name");
        String email = data.getStringExtra("email");
        String contact = data.getStringExtra("contact");

        editName = findViewById(R.id.ed_EditProfName);
        editContact = findViewById(R.id.ed_editProfPhone);
        editEmail = findViewById(R.id.ed_EditprofEmail);
        EditProfileImageView = findViewById(R.id.iv_EditprofImage);

        EditProfileImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(EditUserProfile.this,"profile image clicked",Toast.LENGTH_SHORT).show();
            }
        });

        editEmail.setText(email);
        editContact.setText(contact);
        editName.setText(name);

        Log.d(TAG,"onCreate: " +name + " " +email+ " " +contact);





    }
}
//not completed