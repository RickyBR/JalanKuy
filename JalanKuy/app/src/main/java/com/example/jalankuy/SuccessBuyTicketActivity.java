package com.example.jalankuy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class SuccessBuyTicketActivity extends AppCompatActivity {
    Animation app_splash,btt,ttb;
    Button btn_view_ticket,btn_home;
    TextView app_title,app_subtitle;
    ImageView iconSuccessTicket;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success_buy_ticket);
        btn_view_ticket = findViewById(R.id.btn_viewticket);
        btn_home = findViewById(R.id.btn_home);
        app_title = findViewById(R.id.app_title);
        app_subtitle = findViewById(R.id.app_subtitle);
        iconSuccessTicket = findViewById(R.id.icon_success);
        //animation declare
        app_splash = AnimationUtils.loadAnimation(this, R.anim.app_splash);
        btt  = AnimationUtils.loadAnimation(this,R.anim.btt);
        ttb = AnimationUtils.loadAnimation(this,R.anim.ttb);

        //run animation
        iconSuccessTicket.startAnimation(app_splash);
        app_title.startAnimation(ttb);
        app_subtitle.startAnimation(ttb);

        btn_home.startAnimation(btt);
        btn_view_ticket.startAnimation(btt);

        btn_view_ticket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goViewTicket = new Intent(SuccessBuyTicketActivity.this, MyProfileActivity.class);
                startActivity(goViewTicket);
            }
        });
        btn_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goHome = new Intent(SuccessBuyTicketActivity.this, HomeActivity.class);
                startActivity(goHome);
            }
        });

    }
}