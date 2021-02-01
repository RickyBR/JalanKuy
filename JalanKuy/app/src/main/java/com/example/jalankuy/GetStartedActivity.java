package com.example.jalankuy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class GetStartedActivity extends AppCompatActivity {
    Button btn_signin;
    Button btn_newaccount;
    Animation ttb,btt;
    ImageView emblem_app;
    TextView intro_app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_started);

        ttb = AnimationUtils.loadAnimation(this,R.anim.ttb);
        btt = AnimationUtils.loadAnimation(this,R.anim.btt);

        btn_signin = findViewById(R.id.btn_signin);
        btn_newaccount = findViewById(R.id.btn_newaccount);
        emblem_app = findViewById(R.id.appemblem);
        intro_app = findViewById(R.id.introapp);

        emblem_app.startAnimation(ttb);
        intro_app.startAnimation(ttb);
        btn_signin.startAnimation(btt);
        btn_newaccount.startAnimation(btt);

        btn_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GetStartedActivity.this , SignInActivity.class);
                startActivity(intent);
            }
        });

        btn_newaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gonewacc = new Intent(GetStartedActivity.this, RegisterOneActivity.class);
                startActivity(gonewacc);
            }
        });
    }
}