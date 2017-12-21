package com.example.gm7.instaproject.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.gm7.instaproject.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText mEdUsername;
    private EditText mEdPassword;
    private Button mBtnForgetPassword;
    private Button mBtnLogin;
    private Button mBtnCreateAccount;
    private Button mBtnFacebook;
    private Button mBtnGoogle;


    //firebase auth object
    private FirebaseAuth firebaseAuth;

    //progress dialog
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //getting firebase auth object
        firebaseAuth = FirebaseAuth.getInstance();

        //if the objects getcurrentuser method is not null
        //means user is already logged in
        if (firebaseAuth.getCurrentUser() != null) {
            //close this activity
            finish();
            //opening profile activity
            startActivity(new Intent(getApplicationContext(), WelcomeActivity.class));
        }

        initializeView();
        progressDialog = new ProgressDialog(this);

        mBtnLogin.setOnClickListener(this);
        mBtnCreateAccount.setOnClickListener(this);

    }

    private void initializeView() {
        mEdUsername = findViewById(R.id.ed_username);
        mEdPassword = findViewById(R.id.ed_password);
        mBtnForgetPassword = findViewById(R.id.btn_forget_password);
        mBtnLogin = findViewById(R.id.btn_login);
        mBtnCreateAccount = findViewById(R.id.btn_create_account);
        mBtnFacebook = findViewById(R.id.btn_facebook);
        mBtnGoogle = findViewById(R.id.btn_google);
    }

    //method for user login
    private void userLogin() {
        String email = mEdUsername.getText().toString().trim();
        String password = mEdPassword.getText().toString().trim();


        //checking if email and passwords are empty
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Please enter email", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please enter password", Toast.LENGTH_LONG).show();
            return;
        }

        //if the email and password are not empty
        //displaying a progress dialog

        progressDialog.setMessage("Please Wait...");
        progressDialog.show();

        //logging in the user
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        //if the task is successfull
                        if (task.isSuccessful()) {
                            //start the profile activity
                            finish();
                            startActivity(new Intent(getApplicationContext(), WelcomeActivity.class));
                        }
                    }
                });

    }

    @Override
    public void onClick(View view) {
        if (view == mBtnLogin) {
            userLogin();
        }

        if (view == mBtnCreateAccount) {
            finish();
            startActivity(new Intent(this, SignupActivity.class));
        }
    }

}
