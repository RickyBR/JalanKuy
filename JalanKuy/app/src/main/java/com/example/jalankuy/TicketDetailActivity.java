package com.example.jalankuy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class TicketDetailActivity extends AppCompatActivity {
    LinearLayout btn_back;
    Button btn_buyticket;
    TextView ticket_title, location_ticket,photo_spots,
    wifi_available,festival_spots,short_desc;
    ImageView bg_header_ticket;


    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_detail);
        btn_back = findViewById(R.id.btn_back);
        btn_buyticket = findViewById(R.id.btn_buyticket);

        bg_header_ticket = findViewById(R.id.bg_header_ticket);

        //declare ticket details
        ticket_title = findViewById(R.id.ticket_title);
        location_ticket = findViewById(R.id.ticket_location);
        photo_spots = findViewById(R.id.photo_spots);
        wifi_available = findViewById(R.id.wifi_available);
        festival_spots = findViewById(R.id.festival_spots);
        short_desc = findViewById(R.id.short_desc);


        //mengambil data dari intent
        Bundle bundle = getIntent().getExtras();
        final String jenis_tiket_baru = bundle.getString("jenis tiket");

        //get data from firebase
            reference = FirebaseDatabase.getInstance().getReference().child("Wisata").child(jenis_tiket_baru);
            reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                ticket_title.setText(dataSnapshot.child("nama_wisata").getValue().toString());
                location_ticket.setText(dataSnapshot.child("lokasi").getValue().toString());
                photo_spots.setText(dataSnapshot.child("is_photo_spot").getValue().toString());
                wifi_available.setText(dataSnapshot.child("is_wifi").getValue().toString());
                festival_spots.setText(dataSnapshot.child("is_festival").getValue().toString());
                short_desc.setText(dataSnapshot.child("short_desc").getValue().toString());

                Picasso.with(TicketDetailActivity.this).load(dataSnapshot.child("url_thumbnail")
                        .getValue().toString()).centerCrop().fit().into(bg_header_ticket);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gobackone = new Intent(TicketDetailActivity.this , HomeActivity.class);
                startActivity(gobackone);
            }
        });
        btn_buyticket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gocheckout = new Intent(TicketDetailActivity.this , TicketCheckoutActivity.class);
                gocheckout.putExtra("jenis tiket",jenis_tiket_baru);
                startActivity(gocheckout);
            }
        });
    }
}