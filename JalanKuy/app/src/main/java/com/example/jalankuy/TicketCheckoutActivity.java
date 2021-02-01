    package com.example.jalankuy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;

    public class TicketCheckoutActivity extends AppCompatActivity {
    Button btn_buy_ticket;
    ImageView btn_minus,btn_plus,noticemoney;
    TextView textjumlahtiket, texttotalharga,textmybalance,nama_wisata,lokasi,ketentuan;
    Integer valueJumlahTiket = 1;
    Integer mybalance = 0;
    Integer valuetotalharga = 0;
    Integer valuehargatiket = 0;
    Integer sisa_balance = 0;

    DatabaseReference reference,reference2,reference3,reference4;

    String USERNAME_KEY = "usernamekey";
    String username_key = "";
    String username_key_new = "";

    String date_wisata;
    String time_wisata;
    Integer id_transaksi = new Random().nextInt();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_checkout);
        getUsernameLocal();

        //mengambil data dari intent
        Bundle bundle = getIntent().getExtras();
        final String jenis_tiket_baru = bundle.getString("jenis tiket");

        btn_minus = findViewById(R.id.btn_minus);
        btn_plus = findViewById(R.id.btn_plus);

        textjumlahtiket= findViewById(R.id.jumlah_ticket);
        texttotalharga= findViewById(R.id.totalharga);
        textmybalance= findViewById(R.id.mybalance);
        noticemoney= findViewById(R.id.noticemoney);

        nama_wisata = findViewById(R.id.nama_wisata);
        lokasi = findViewById(R.id.lokasi);
        ketentuan = findViewById(R.id.ketentuan);

        textjumlahtiket.setText(valueJumlahTiket.toString());



        btn_minus.animate().alpha(0).setDuration(300).start();
        btn_minus.setEnabled(false);



        noticemoney.setVisibility(View.GONE);
        //get user data from firebase

        reference2 = FirebaseDatabase.getInstance().getReference().child("Users").child(username_key_new);
        reference2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mybalance = Integer.valueOf(dataSnapshot.child("user_balance").getValue().toString());
                textmybalance.setText("US$ "+ mybalance+"");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        reference = FirebaseDatabase.getInstance().getReference().child("Wisata").child(jenis_tiket_baru);

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                nama_wisata.setText(dataSnapshot.child("nama_wisata").getValue().toString());
                lokasi.setText(dataSnapshot.child("lokasi").getValue().toString());
                ketentuan.setText(dataSnapshot.child("ketentuan").getValue().toString());
                date_wisata =dataSnapshot.child("date_wisata").getValue().toString();
                time_wisata =dataSnapshot.child("time_wisata").getValue().toString();
                valuehargatiket = Integer.valueOf(dataSnapshot.child("harga_tiket").getValue().toString());

                valuetotalharga = valuehargatiket * valueJumlahTiket;
                texttotalharga.setText("US$" + valuetotalharga+"");
                
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        btn_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                valueJumlahTiket+=1;
                textjumlahtiket.setText(valueJumlahTiket.toString());
                if(valueJumlahTiket >1 ){
                    btn_minus.animate().alpha(1).setDuration(300).start();
                    btn_minus.setEnabled(true);

                }
                valuetotalharga = valuehargatiket * valueJumlahTiket;
                texttotalharga.setText("US$" + valuetotalharga+"");
                if(valuetotalharga > mybalance){
                    btn_buy_ticket.animate().translationY(250).alpha(0).setDuration(350).start();
                    btn_buy_ticket.setEnabled(false);
                    textmybalance.setTextColor(Color.parseColor("#D1208B"));
                    noticemoney.setVisibility(View.VISIBLE);
                }
            }
        });
        btn_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                valueJumlahTiket-=1;
                textjumlahtiket.setText(valueJumlahTiket.toString());
                if(valueJumlahTiket < 2){
                    btn_minus.animate().alpha(0).setDuration(300).start();
                    btn_minus.setEnabled(false);

                }
                valuetotalharga = valuehargatiket * valueJumlahTiket;
                texttotalharga.setText("US$" + valuetotalharga+"");
                if(valuetotalharga < mybalance){
                    btn_buy_ticket.animate().translationY(0).alpha(1).setDuration(350).start();
                    btn_buy_ticket.setEnabled(true);
                    textmybalance.setTextColor(Color.parseColor("#203DD1"));
                    noticemoney.setVisibility(View.GONE);
                }
            }
        });



        btn_buy_ticket = findViewById(R.id.btn_buyticket);
        btn_buy_ticket.setOnClickListener((v)->{
            //save data to firebase and create new table name"MyTickets"
            reference3 = FirebaseDatabase.getInstance().getReference().child("MyTickets").child(username_key_new)
                    .child(nama_wisata.getText().toString() + id_transaksi);
            reference3.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    reference3.getRef().child("id_tiket").setValue(nama_wisata.getText().toString() + id_transaksi);
                    reference3.getRef().child("nama_wisata").setValue(nama_wisata.getText().toString());
                    reference3.getRef().child("lokasi").setValue(lokasi.getText().toString());
                    reference3.getRef().child("ketentuan").setValue(ketentuan.getText().toString());
                    reference3.getRef().child("jumlah_tiket").setValue(valueJumlahTiket.toString());
                    reference3.getRef().child("date_wisata").setValue(date_wisata);
                    reference3.getRef().child("time_wisata").setValue(time_wisata);


                    Intent gotosuccesview = new Intent(TicketCheckoutActivity.this , SuccessBuyTicketActivity.class);
                    startActivity(gotosuccesview);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
            //update user balance after buy ticket
            //get user data from firebase
            reference4 = FirebaseDatabase.getInstance().getReference().child("Users").child(username_key_new);
            reference4.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    sisa_balance = mybalance - valuetotalharga;
                    reference4.getRef().child("user_balance").setValue(sisa_balance);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        });

    }
    public void getUsernameLocal(){
        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
        username_key_new = sharedPreferences.getString(username_key, "");
    }
}