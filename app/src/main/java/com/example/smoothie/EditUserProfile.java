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

import java.util.HashMap;
import java.util.Map;

public class EditUserProfile extends AppCompatActivity {

    public static final String TAG = "TAG";

    EditText editName , editContact, editEmail;
    ImageView EditProfileImageView;
    Button saveBtn;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user_profile);

        Intent data = getIntent();
        final String name = data.getStringExtra("name");
        String email = data.getStringExtra("email");
        String contact = data.getStringExtra("contact");


        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        user = fAuth.getCurrentUser();


        editName = findViewById(R.id.ed_EditProfName);
        editContact = findViewById(R.id.ed_editProfPhone);
        editEmail = findViewById(R.id.ed_EditprofEmail);
        EditProfileImageView = findViewById(R.id.iv_EditprofImage);
        saveBtn = findViewById(R.id.btn_save);

        EditProfileImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(EditUserProfile.this,"profile image clicked",Toast.LENGTH_SHORT).show();
            }
        });

        //update edited user profile data
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(editName.getText().toString().isEmpty() || editContact.getText().toString().isEmpty() || editEmail.getText().toString().isEmpty()){
                    Toast.makeText(EditUserProfile.this,"one or many fields are empty",Toast.LENGTH_SHORT).show();
                    return ;

                }

                final String email = editEmail.getText().toString();
                user.updateEmail(email).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        DocumentReference docRef = fStore.collection("Users").document(user.getUid());

                        Map<String,Object> edited = new HashMap<>();
                        edited.put("email",editEmail.getText().toString());
                        edited.put("name",editName.getText().toString());
                        edited.put("contact",editContact.getText().toString());

                        docRef.update(edited).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(EditUserProfile.this,"Profile updated",Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(),UserProfile.class));
                                finish();

                            }
                        });

                        Toast.makeText(EditUserProfile.this,"Email is changed",Toast.LENGTH_SHORT).show();

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(EditUserProfile.this, e.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

        editEmail.setText(email);
        editContact.setText(contact);
        editName.setText(name);

        Log.d(TAG,"onCreate: " +name + " " +email+ " " +contact);




    }
}
//not completed