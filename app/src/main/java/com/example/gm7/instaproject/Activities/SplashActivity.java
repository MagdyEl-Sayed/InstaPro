package com.example.gm7.instaproject.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.gm7.instaproject.R;
import com.google.firebase.auth.FirebaseAuth;

public class SplashActivity extends AppCompatActivity {

    private final static int SPLASH_TIME_OUT = 3000;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        //to hide the actionbar of the screen
        assert getSupportActionBar() != null;
        getSupportActionBar().hide();

        mAuth = FirebaseAuth.getInstance();

        Thread timer = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(SPLASH_TIME_OUT);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    Intent loginIntent ;
                    if (mAuth.getCurrentUser() != null){
                        loginIntent = new Intent(SplashActivity.this,WelcomeActivity.class);
                    } else {
                        loginIntent = new Intent(SplashActivity.this,LoginActivity.class);
                    }
                    loginIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK
                    |Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(loginIntent);
                }
            }
        });
        timer.start();
    }
}
