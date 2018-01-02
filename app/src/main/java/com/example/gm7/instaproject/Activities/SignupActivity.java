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

import com.example.gm7.instaproject.Model.UserModel;
import com.example.gm7.instaproject.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by GM7 on 21/12/2017.
 */

public class SignupActivity extends AppCompatActivity {

    //defining view objects
    private EditText editTextEmail;
    private EditText editTextPassword;
    private Button buttonSignup;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase database;
    private DatabaseReference reference;
    private String userId;
    private Button mBtnHaveAccount;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        assert getSupportActionBar() != null;
        getSupportActionBar().hide();

        //initializing firebase auth object
        firebaseAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        reference = database.getReference();

        //initializing views
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        buttonSignup = (Button) findViewById(R.id.btn_signup);
        mBtnHaveAccount = findViewById(R.id.btn_have_account);

        //attaching listener to button
        buttonSignup.setOnClickListener(onRegisterClicked());
        mBtnHaveAccount.setOnClickListener(onHaveAccountClicked());
    }

    private void registerUser(){

        //getting email and password from edit texts
        final String email = editTextEmail.getText().toString().trim();
        String password  = editTextPassword.getText().toString().trim();

        //checking if email and passwords are empty
        if(TextUtils.isEmpty(email)){
            editTextEmail.setError(getString(R.string.invalid_input_error));
        }else if(TextUtils.isEmpty(password)){
           editTextPassword.setError(getString(R.string.invalid_input_error));
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError(getString(R.string.invalid_email_error));
        } else {
            //creating a new user
            firebaseAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            //checking if success
                            if(task.isSuccessful()){
                                assert firebaseAuth.getCurrentUser() !=null;
                                userId = firebaseAuth.getCurrentUser().getUid();
                                uploadUserInfo(email);
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

    private void uploadUserInfo(String email){

        String key = reference.child(userId).push().getKey();
        Map<String,Object> userValue = new UserModel().insertUser(userId,email);
        Map<String,Object> childUpdates = new HashMap<>();
        childUpdates.put("users/"+key,userValue);
        reference.updateChildren(childUpdates).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    launchMainScreen();
                } else {
                    showErrorDialog();
                }
            }
        });


    }

    private void launchMainScreen(){
        Intent intent = new Intent(SignupActivity.this,WelcomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK
                |Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    private View.OnClickListener onHaveAccountClicked(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignupActivity.this,LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP |
                        Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        };
    }
}
