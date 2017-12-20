package com.example.gm7.instaproject.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.gm7.instaproject.R;

public class LoginActivity extends AppCompatActivity {
    private EditText mEdUsername;
    private EditText mEdPassword;
    private Button mBtnForgetPassword;
    private Button mBtnLogin;
    private Button mBtnCreateAccount;
    private Button mBtnFacebook;
    private Button mBtnGoogle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initializeView();

        mBtnLogin.setOnClickListener(onLoginBtnClicked());


    }
    private void initializeView(){
        mEdUsername = findViewById(R.id.ed_username);
        mEdPassword = findViewById(R.id.ed_password);
        mBtnForgetPassword = findViewById(R.id.btn_forget_password);
        mBtnLogin = findViewById(R.id.btn_login);
        mBtnCreateAccount = findViewById(R.id.btn_create_account);
        mBtnFacebook = findViewById(R.id.btn_facebook);
        mBtnGoogle = findViewById(R.id.btn_google);
    }


    private View.OnClickListener onLoginBtnClicked(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginIntent = new Intent(LoginActivity.this,WelcomeActivity.class);
                loginIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK
                        |Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(loginIntent);
            }
        };
    }
}
