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

import com.example.gm7.instaproject.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
public class LoginActivity extends AppCompatActivity  {
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

        initializeView();
        progressDialog = new ProgressDialog(this);

        mBtnLogin.setOnClickListener(onLoginClicked());
        mBtnCreateAccount.setOnClickListener(onCreateClicked());

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
            mEdUsername.setError(getString(R.string.invalid_input_error));
        } else if (TextUtils.isEmpty(password)) {
            mEdPassword.setError(getString(R.string.invalid_input_error));
        } else {
            //logging in the user
            firebaseAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            progressDialog.dismiss();
                            //if the task is successfull
                            if (task.isSuccessful()) {
                                //start the profile activity
                               Intent intent =new Intent(getApplicationContext(), WelcomeActivity.class);
                               intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP |
                               Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                            }
                        }
                    });
        }


    }

    private View.OnClickListener onLoginClicked(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userLogin();
            }
        };
    }

    private View.OnClickListener onCreateClicked(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(LoginActivity.this, SignupActivity.class));
            }
        };
    }


}
