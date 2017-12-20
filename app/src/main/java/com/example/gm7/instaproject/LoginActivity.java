package com.example.gm7.instaproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LoginActivity extends AppCompatActivity {
Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
       btn=(Button)findViewById(R.id.btn);
       btn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent loginIntent = new Intent(LoginActivity.this,WelcomeActivity.class);
               loginIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK
                       |Intent.FLAG_ACTIVITY_CLEAR_TOP);
               startActivity(loginIntent);
           }
       });


    }
}
