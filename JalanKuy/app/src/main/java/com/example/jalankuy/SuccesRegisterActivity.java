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

public class SuccesRegisterActivity extends AppCompatActivity {

    Button btn_explore;
    Animation app_splash,btt,ttb;
    ImageView icon_success;
    TextView app_title,app_subtitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_succes_register);

        btn_explore = findViewById(R.id.btn_explore);
        icon_success = findViewById(R.id.icon_success);
        app_title = findViewById(R.id.app_title);
        app_subtitle = findViewById(R.id.app_subtitle);

        btn_explore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goexplore = new Intent(SuccesRegisterActivity.this, HomeActivity.class);
                startActivity(goexplore);
            }
        });
        app_splash = AnimationUtils.loadAnimation(this, R.anim.app_splash);

        btt= AnimationUtils.loadAnimation(this, R.anim.btt);
        ttb = AnimationUtils.loadAnimation(this, R.anim.ttb);
        btt  = AnimationUtils.loadAnimation(this,R.anim.btt);

        btn_explore.startAnimation(btt);
        icon_success.startAnimation(app_splash);
        app_title.startAnimation(ttb);
        app_subtitle.startAnimation(ttb);




    }
}