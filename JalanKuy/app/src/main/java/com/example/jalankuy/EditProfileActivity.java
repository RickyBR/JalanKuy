package com.example.jalankuy;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class EditProfileActivity extends AppCompatActivity {

    Button btn_save,btn_add_new_photo;

    String USERNAME_KEY = "usernamekey";
    String username_key = "";
    String username_key_new = "";
    EditText xnama_lengkap,xbio,xusername,xpassword,xemail_address;
    Uri photo_location;
    Integer photo_max = 1;

    DatabaseReference reference;
    StorageReference storage;
    ImageView photo_edit_profile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        getUsernameLocal();
        photo_edit_profile = findViewById(R.id.pic_photo_edit_profile);
        xnama_lengkap = findViewById(R.id.tv_nama_lengkap);
        xbio = findViewById(R.id.tv_bio);
        xusername = findViewById(R.id.tv_username);
        xpassword = findViewById(R.id.tv_password);
        xemail_address = findViewById(R.id.tv_email);
        btn_add_new_photo = findViewById(R.id.btn_add_new_photo);
        btn_save = findViewById(R.id.btn_save);

        reference = FirebaseDatabase.getInstance().getReference().child("Users").child(username_key_new);
        storage = FirebaseStorage.getInstance().getReference().child("Photousers").child(username_key_new);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                xnama_lengkap.setText(dataSnapshot.child("nama_lengkap").getValue().toString());
                xbio.setText(dataSnapshot.child("bio").getValue().toString());
                xusername.setText(dataSnapshot.child("username").getValue().toString());
                xpassword.setText(dataSnapshot.child("password").getValue().toString());
                xemail_address.setText(dataSnapshot.child("email_address").getValue().toString());

                Picasso.with(EditProfileActivity.this).load(dataSnapshot.child("url_photo_profile")
                        .getValue().toString()).centerCrop().fit().into(photo_edit_profile);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        dataSnapshot.getRef().child("nama_lengkap").setValue(xnama_lengkap.getText().toString());
                        dataSnapshot.getRef().child("bio").setValue(xbio.getText().toString());
                        dataSnapshot.getRef().child("username").setValue(xusername.getText().toString());
                        dataSnapshot.getRef().child("password").setValue(xpassword.getText().toString());
                        dataSnapshot.getRef().child("email_address").setValue(xemail_address.getText().toString());

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                if (photo_location != null){
                    StorageReference storageReference1 =
                            storage.child(System.currentTimeMillis() + "." +
                                    getFileExtension(photo_location));

                    storageReference1.putFile(photo_location)
                            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    storageReference1.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            String uri_photo = uri.toString();
                                            reference.getRef().child("url_photo_profile").setValue(uri_photo);

                                        }
                                    }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Uri> task) {
                                            // berpindah activity
                                            Intent goprofile = new Intent(EditProfileActivity.this,MyProfileActivity.class);
                                            startActivity(goprofile);
                                        }
                                    });

                                }
                            }).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {

                        }
                    });
                }
            }
        });
         btn_add_new_photo.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 findPhoto();
             }
         });

    }
    String getFileExtension(Uri uri){
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    public void findPhoto(){
        Intent pic = new Intent();
        pic.setType("image/*");
        pic.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(pic, photo_max);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == photo_max && resultCode == RESULT_OK && data != null && data.getData() != null)
        {

            photo_location = data.getData();
            Picasso.with(this).load(photo_location).centerCrop().fit().into(photo_edit_profile);

        }

    }
    public void getUsernameLocal(){
        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
        username_key_new = sharedPreferences.getString(username_key, "");
    }
}