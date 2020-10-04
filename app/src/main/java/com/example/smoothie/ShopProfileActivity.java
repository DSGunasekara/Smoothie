package com.example.smoothie;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class ShopProfileActivity extends AppCompatActivity {

    TextView appName, shopName, shopLocation, ownerName, shopEmail, shopPhone;
    ImageView logo;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userID;
    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_profile);

        appName = findViewById(R.id.txtAppName2);
        shopName = findViewById(R.id.txtShopNameLabel);
        shopLocation = findViewById(R.id.txtShopLocationLabel);
        ownerName = findViewById(R.id.txtOwnerLabel);
        shopEmail = findViewById(R.id.txtEmailLabel);
        shopPhone = findViewById(R.id.txtPhoneLabel);
        logo = findViewById(R.id.imgLogo);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();

        //create directory in firebase storage with user ID
//        StorageReference profileRef = storageReference.child("users/"+fAuth.getCurrentUser().getUid()+"/profile.jpg");
//        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//
//            @Override
//            public void onSuccess(Uri uri) {
//                Picasso.get().load(uri).into(logo);
//
//            }
//        });

        userID = fAuth.getCurrentUser().getUid();

        final DocumentReference documentReference = fStore.collection("shop owners").document(shopName.toString());
        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                if(documentSnapshot.exists()) {
                    ownerName.setText(documentSnapshot.getString("owner"));
                    shopEmail.setText(documentSnapshot.getString("email"));
                    shopPhone.setText(documentSnapshot.getString("phone"));
                    shopName.setText(documentSnapshot.getString("name"));
                    shopLocation.setText(documentSnapshot.getString("location"));
                }else{
                    Log.d("tag","onEvent: document do not exists");
                }


            }
        });



       /* btnEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(view.getContext(),EditUserProfile.class);
                startActivity(intent);

                intent.putExtra("contact",txtProfPhone.getText().toString());
                intent.putExtra("name",txtProfName.getText().toString());
                intent.putExtra("email",txtProfEmail.getText().toString());

                //open Gallery
//                Intent openGalleryIntent = new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                startActivityForResult(openGalleryIntent,1000);
            }
        });*/



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1000){
            if(resultCode == Activity.RESULT_OK){
                Uri imageUri = data.getData();

                uploadImageToFirebase(imageUri);

            }
        }

    }

    private void uploadImageToFirebase(Uri imageUri) {
        //in this we are overwrite same image with new one because user can only have one profile image
        //upload image to firebase storage
        final StorageReference fileRef = storageReference.child("users/" + fAuth.getCurrentUser().getUid() + "/profile.jpg");
        fileRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                //Toast.makeText(UserProfile.this,"Image Uploaded",Toast.LENGTH_SHORT).show();
                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Picasso.get().load(uri).into(logo);


                    }
                });

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ShopProfileActivity.this, "Failed", Toast.LENGTH_SHORT).show();


            }
        });

    }
}