package com.example.jalankuy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.contentcapture.DataRemovalRequest;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignInActivity extends AppCompatActivity {

    TextView btn_new_account;
    Button btn_signin;
    EditText xusername,xpassword;

    String USERNAME_KEY = "usernamekey";
    String username_key = "";

    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        btn_new_account = findViewById(R.id.btn_newaccount);
        btn_signin = findViewById(R.id.btn_signin);
        xusername = findViewById(R.id.tv_username);
        xpassword = findViewById(R.id.tv_password);
        btn_new_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignInActivity.this, RegisterOneActivity.class);
                startActivity(intent);
            }
        });
        btn_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_signin.setEnabled(false);
                btn_signin.setText("Loading ...");

                final String username = xusername.getText().toString();
                final String password = xpassword.getText().toString();

               if(username.isEmpty()){
                   Toast.makeText(getApplicationContext(),"Username belum terisi!!",Toast.LENGTH_SHORT).show();
                   btn_signin.setEnabled(true);
                   btn_signin.setText("Sign In");

               } else{
                   if(password.isEmpty()){
                       Toast.makeText(getApplicationContext(),"Password belum terisi!!",Toast.LENGTH_SHORT).show();
                       btn_signin.setEnabled(true);
                       btn_signin.setText("Sign In");

                   } else {
                       reference = FirebaseDatabase.getInstance().getReference().child("Users").child(username);
                       reference.addListenerForSingleValueEvent(new ValueEventListener() {
                           @Override
                           public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                               if(datasnapshot.exists()){
                                   //ambil data password from firebase
                                   String passwordfromdb = datasnapshot.child("password").getValue().toString();

                                   //validate password
                                   if(password.equals(passwordfromdb)){


                                       //menyimpan data kepada local storage
                                       SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY,MODE_PRIVATE);
                                       SharedPreferences.Editor editor = sharedPreferences.edit();
                                       editor.putString(username_key,xusername.getText().toString());
                                       editor.apply();

                                       //berpindah activity
                                       Intent intent1 = new Intent(SignInActivity.this, HomeActivity.class);
                                       startActivity(intent1);
                                   }
                                   else {
                                       Toast.makeText(getApplicationContext(), "Maaf,Password salah!",Toast.LENGTH_SHORT).show();
                                       btn_signin.setEnabled(true);
                                       btn_signin.setText("Sign In");
                                   }

                               } else {
                                   Toast.makeText(getApplicationContext(), "Maaf,Username Salah!",Toast.LENGTH_SHORT).show();
                                   btn_signin.setEnabled(true);
                                   btn_signin.setText("Sign In");
                               }
                           }

                           @Override
                           public void onCancelled(@NonNull DatabaseError error) {
                               Toast.makeText(getApplicationContext(), "Database Error!!",Toast.LENGTH_SHORT).show();


                           }
                       });
                   }
               }



            }
        });

    }
}