package com.example.smoothie;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.WriteBatch;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.List;

public class UserProfile extends AppCompatActivity {
    public static final String TAG = "TAG";
    ImageView profImage;
    TextView txtProfName, txtProfEmail, txtProfPhone;
    ImageView btnEditProfile , btnDeleteProfile;
    FirebaseAuth fAuth;
    //FirebaseDatabase firebaseDatabase;
    FirebaseFirestore fStore;
    String userID;
    StorageReference storageReference;

    String EMAIL;

    //private String contact;
    //private String phone, password,email;
    //private static final String USERS = "Users";
    // private final String TAG = this.getClass().getName().toUpperCase();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        txtProfName = findViewById(R.id.tv_EditprofName);
        txtProfEmail = findViewById(R.id.tv_EditProfEmail);
        txtProfPhone = findViewById(R.id.tv_EditprofPhone);

        profImage = findViewById(R.id.iv_EditprofImage);
        btnEditProfile = findViewById(R.id.btnEditProfile);
        btnDeleteProfile = findViewById(R.id.btnDelete);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();

        EMAIL = String.valueOf(txtProfEmail);

        //create directory in firebase storage with user ID
        StorageReference profileRef = storageReference.child("users/"+fAuth.getCurrentUser().getUid()+"/profile.jpg");
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {

            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(profImage);

            }
        });




        userID = fAuth.getCurrentUser().getUid();

        //retrieve the user data

        DocumentReference documentReference = fStore.collection("Users").document(userID);
        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                if(documentSnapshot.exists()) {
                    txtProfPhone.setText(documentSnapshot.getString("contact"));
                    txtProfName.setText(documentSnapshot.getString("name"));
                    txtProfEmail.setText(documentSnapshot.getString("email"));
                }else{
                    Log.d("tag","onEvent: document do not exists");
                }
            }
        });



        btnEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(view.getContext(),EditUserProfile.class);
                intent.putExtra("contact",txtProfPhone.getText().toString());
                intent.putExtra("name",txtProfName.getText().toString());
                intent.putExtra("email",txtProfEmail.getText().toString());
                startActivity(intent);



                //open Gallery
//                Intent openGalleryIntent = new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                startActivityForResult(openGalleryIntent,1000);
            }
        });





        //Delete user profile
//        btnDeleteProfile.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                FirebaseFirestore.getInstance().collection("Users")
//                        .whereEqualTo("email", EMAIL)
//                        .get()
//                        .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
//                            @Override
//                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
//
//                                WriteBatch batch =  FirebaseFirestore.getInstance().batch();
//                                List<DocumentSnapshot> snapshots = queryDocumentSnapshots.getDocuments();
//                                for(DocumentSnapshot snapshot: snapshots){
//                                    batch.delete(snapshot.getReference());
//                                }
//                                batch.commit()
//                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
//                                            @Override
//                                            public void onSuccess(Void aVoid) {
//                                                Log.d(TAG, "onSuccess: Delete all docs with email = email");
//                                            }
//                                        }).addOnFailureListener(new OnFailureListener() {
//                                    @Override
//                                    public void onFailure(@NonNull Exception e) {
//                                        Log.e(TAG, "onFailure: ", e);
//                                    }
//                                });
//                            }
//                        }).addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//
//                    }
//                });
//            }
//        });

        btnDeleteProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteAccount();
            }
        });






    }
    private void deleteAccount() {
        Log.d(TAG, "ingreso a deleteAccount");
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        final FirebaseUser currentUser = firebaseAuth.getCurrentUser();

        currentUser.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Log.d(TAG,"OK! Works fine!");
                    startActivity(new Intent(UserProfile.this, MainActivity.class));
                    finish();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e(TAG,"Ocurrio un error durante la eliminación del usuario", e);
            }
        });
    }

//    private void deleteAccount() {
//        AuthCredential credential = EmailAuthProvider.getCredential();
//
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1000){
            if(resultCode == Activity.RESULT_OK){
                Uri imageUri = data.getData();


                //profImage.setImageURI(imageUri);


                uploadImageToFirebase(imageUri);

            }
        }

    }

    private void uploadImageToFirebase(Uri imageUri) {
        //in this we are overwrite same image with new one because user can only have one profile image
        //upload image to firebase storage
        final StorageReference fileRef = storageReference.child("users/"+fAuth.getCurrentUser().getUid()+"/profile.jpg");
        fileRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                //Toast.makeText(UserProfile.this,"Image Uploaded",Toast.LENGTH_SHORT).show();
                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Picasso.get().load(uri).into(profImage);


                    }
                });

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(UserProfile.this,"Failed",Toast.LENGTH_SHORT).show();



            }
        });


    }


}










//String contact = txtProfPhone.getText();

//        Intent intent = getIntent();
//        email = intent.getStringExtra("email");

//        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
//        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
//        //FirebaseDatabase.getInstance().setPersistenceEnabled(true);
//        DatabaseReference userRef = rootRef.child(USERS);
//        Log.v("email", userRef.getKey());



//        profImage = findViewById(R.id.iv_profImage);
//        txtProfEmail = findViewById(R.id.tv_ProfEmail);
//        txtProfName = findViewById(R.id.tv_profName);
//        txtProfPhone = findViewById(R.id.tv_profPhone);
//        btnEditProfile = findViewById(R.id.btnEditProfile);
//
//
//        fAuth = FirebaseAuth.getInstance();
//        //firebaseDatabase = FirebaseDatabase.getInstance();
//        fStore = FirebaseFirestore.getInstance();
//
//
//        userID = fAuth.getCurrentUser().getUid();
//
//        DocumentReference documentReference = fStore.collection("users").document(userID);//retrieve data from database
//        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
//            @Override
//            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
//                txtProfName.setText(documentSnapshot.getString("Name"));
//                txtProfPhone.setText(documentSnapshot.getString("Mobile"));
//                txtProfEmail.setText(documentSnapshot.getString("Email"));
//
//            }
//        });
//
//
//
//        profImage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //open gallery
//
//                Intent openGalleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                startActivityForResult(openGalleryIntent,1000);
//            }
//        });
//
//
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @androidx.annotation.Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == 1000) {
//            if (resultCode == Activity.RESULT_OK) {
//                Uri imageUri = data.getData();
//                profImage.setImageURI(imageUri);
//
//            }
//
//        }
//
//    }
















//        DatabaseReference databaseReference = firebaseDatabase.getReference(firebaseAuth.getUid());
//        databaseReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//                Users users = dataSnapshot.getValue(Users.class);
//
//                txtProfPhone.setText(users.getContact());
//                txtProfName.setText(users.getName());
//                txtProfEmail.setText(users.getEmail());
//
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//                Toast.makeText(UserProfile.this,databaseError.getCode(),Toast.LENGTH_SHORT).show();
//
//            }
//        });






//        userRef.addValueEventListener(new ValueEventListener() {
//            String nName , nEmail, nPhone ;
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                for(DataSnapshot ds : dataSnapshot.getChildren() ){
//                    if(ds.child("email").getValue().equals(txtProfEmail)){
//                        nName = ds.child("name").getValue(String.class);
//                        nEmail = ds.child("email").getValue(String.class);
//                        nPhone = ds.child("contact").getValue(String.class);
//                        break;
//
//                    }
//                }
//
//
//                txtProfPhone.setText(nPhone);
//                txtProfEmail.setText(nEmail);
//                txtProfName.setText(nName);
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//
//                Log.w(TAG,"Failed to read", databaseError.toException());
//
//            }
//        });



//        DatabaseReference readRef = FirebaseDatabase.getInstance().getReference().child("Users");
//
//        readRef.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
//                    if (dataSnapshot1.child("email").getValue().equals(txtProfEmail)) {
//                        if(dataSnapshot.hasChildren()) {
//                            txtProfEmail.setText(dataSnapshot.child("email").getValue(String.class).toString());
//                            txtProfName.setText(dataSnapshot.child("name").getValue(String.class).toString());
//                            txtProfPhone.setText(dataSnapshot.child("contact").getValue(String.class).toString());
//                        }
//                    } else {
//                        Toast.makeText(getApplicationContext(), "No source to display", Toast.LENGTH_SHORT).show();
//                    }
//                }
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });

//    public void logout(View view){
//       FirebaseAuth.getInstance().signOut(); //logout
//        startActivity(new Intent(getApplicationContext(),MainActivity.class));
//        finish();
//    }
//
//
//
//}