    package com.example.jalankuy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

    public class SplashActivity extends AppCompatActivity {

        Animation app_splash, btt;
        ImageView app_logo;
        TextView app_subtitle;

        String USERNAME_KEY = "usernamekey";
        String username_key = "";
        String username_key_new = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getUsernameLocal();

        app_splash = AnimationUtils.loadAnimation(this, R.anim.app_splash);
        btt = AnimationUtils.loadAnimation(this, R.anim.btt);
        app_logo = findViewById(R.id.applogo);
        app_subtitle = findViewById(R.id.app_subtitle);

        app_logo.startAnimation(app_splash);
        app_subtitle.startAnimation(btt);


    }
        public void getUsernameLocal(){
            SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
            username_key_new = sharedPreferences.getString(username_key, "");
            if(username_key_new.isEmpty()){
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent gogetstarted = new Intent(SplashActivity.this , GetStartedActivity.class);
                        startActivity(gogetstarted);
                    }
                }, 2000);
            } else{
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent gohome = new Intent(SplashActivity.this , HomeActivity.class);
                        startActivity(gohome);
                    }
                }, 2000);
            }
        }
}