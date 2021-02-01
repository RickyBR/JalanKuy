package com.example.jalankuy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.florent37.shapeofview.shapes.CircleView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class HomeActivity extends AppCompatActivity {
    LinearLayout btn_pisa,btn_torri,btn_pagoda,btn_candi,btn_sphinx,btn_monas;
    CircleView btn_profile;
    ImageView photo_user;
    TextView user_balance,nama_lengkap,bio;
    String USERNAME_KEY = "usernamekey";
    String username_key = "";
    String username_key_new = "";

    DatabaseReference reference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        getUsernameLocal();

        btn_pisa = findViewById(R.id.btn_pisa);
        btn_torri = findViewById(R.id.btn_tori);
        btn_candi = findViewById(R.id.btn_candi);
        btn_pagoda = findViewById(R.id.btn_pagoda);
        btn_sphinx = findViewById(R.id.btn_sphinx);
        btn_monas = findViewById(R.id.btn_monas);
        btn_profile = findViewById(R.id.btn_profile);
        photo_user = findViewById(R.id.pic_photo_home_user);
        user_balance = findViewById(R.id.user_balance);
        nama_lengkap = findViewById(R.id.nama_lengkap);
        bio = findViewById(R.id.bio);

        reference = FirebaseDatabase.getInstance().getReference().child("Users").child(username_key_new);

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                nama_lengkap.setText(dataSnapshot.child("nama_lengkap").getValue().toString());
                bio.setText(dataSnapshot.child("bio").getValue().toString());
                user_balance.setText("US$ " + dataSnapshot.child("user_balance").getValue().toString());
                Picasso.with(HomeActivity.this).load(dataSnapshot.child("url_photo_profile").getValue().toString()).centerCrop().fit().into(photo_user);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        btn_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gopisa = new Intent(HomeActivity.this , MyProfileActivity.class);
                startActivity(gopisa);
            }
        });

        btn_pisa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gopisa = new Intent(HomeActivity.this , TicketDetailActivity.class);
                gopisa.putExtra("jenis tiket","pisa");
                startActivity(gopisa);
            }
        });
        btn_torri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotorri = new Intent(HomeActivity.this , TicketDetailActivity.class);
                gotorri.putExtra("jenis tiket","Torri");
                startActivity(gotorri);
            }
        });
        btn_pagoda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gopagoda = new Intent(HomeActivity.this , TicketDetailActivity.class);
                gopagoda.putExtra("jenis tiket","Pagoda");
                startActivity(gopagoda);
            }
        });

        btn_candi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gocandi = new Intent(HomeActivity.this , TicketDetailActivity.class);
                gocandi.putExtra("jenis tiket","Candi");
                startActivity(gocandi);
            }
        });

        btn_sphinx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gosphinx = new Intent(HomeActivity.this , TicketDetailActivity.class);
                gosphinx.putExtra("jenis tiket","Sphinx");
                startActivity(gosphinx);
            }
        });

        btn_monas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gomonas = new Intent(HomeActivity.this , TicketDetailActivity.class);
                gomonas.putExtra("jenis tiket","Monas");
                startActivity(gomonas);
            }
        });

    }

    public void getUsernameLocal(){
        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
        username_key_new = sharedPreferences.getString(username_key, "");
    }
}