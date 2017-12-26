package com.example.gm7.instaproject.Activities;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.gm7.instaproject.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by GM7 on 21/12/2017.
 */

public class SignupActivity extends AppCompatActivity {

    //defining view objects
    private EditText editTextEmail;
    private EditText editTextPassword;
    private Button buttonSignup;


    //defining firebaseauth object
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        //initializing firebase auth object
        firebaseAuth = FirebaseAuth.getInstance();

        //initializing views
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);

        buttonSignup = (Button) findViewById(R.id.buttonSignup);



        //attaching listener to button
        buttonSignup.setOnClickListener(onRegisterClicked());
    }

    private void registerUser(){

        //getting email and password from edit texts
        String email = editTextEmail.getText().toString().trim();
        String password  = editTextPassword.getText().toString().trim();

        //checking if email and passwords are empty
        if(TextUtils.isEmpty(email)){
            editTextEmail.setError(getString(R.string.invalid_input_error));
        }else if(TextUtils.isEmpty(password)){
           editTextPassword.setError(getString(R.string.invalid_input_error));
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setText(getString(R.string.invalid_email_error));
        } else {
            //creating a new user
            firebaseAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            //checking if success
                            if(task.isSuccessful()){
                                Intent intent = new Intent(SignupActivity.this,WelcomeActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK
                                        |Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                            }else{
                                showErrorDialog();

                            }

                        }
                    });
        }



    }

    private View.OnClickListener onRegisterClicked(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser();
            }
        };
    }

    private void showErrorDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(SignupActivity.this);
        builder.setTitle(getString(R.string.register_error_title))
                .setMessage(getString(R.string.register_error_msg))
                .setPositiveButton(android.R.string.ok,null)
                .setNegativeButton(android.R.string.cancel,null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
