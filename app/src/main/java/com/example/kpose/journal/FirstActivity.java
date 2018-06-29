package com.example.kpose.journal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.kpose.journal.user_sign.LoginActivity;
import com.example.kpose.journal.user_sign.RegisterActivity;
import com.google.firebase.auth.FirebaseAuth;

public class FirstActivity extends AppCompatActivity {
    private Button signin_button,signup_button;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        signin_button = findViewById(R.id.signin_button);
        signup_button = findViewById(R.id.signup_button);

        firebaseAuth = FirebaseAuth.getInstance();

        updateUI();

        signup_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });

        signin_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });


    }

    private void signup(){
        Intent signupIntent = new Intent(FirstActivity.this, RegisterActivity.class);
        startActivity(signupIntent);
    }

    private void login(){
        Intent logIntent = new Intent(FirstActivity.this, LoginActivity.class);
        startActivity(logIntent);

    }

    private void updateUI(){
        if (firebaseAuth.getCurrentUser() != null) {
            Log.i("FirstActivity", "firebaseAuth:= null");
            Intent startIntent = new Intent(FirstActivity.this, MainActivity.class);
            startActivity(startIntent);
            finish();
        }else {

            Log.i("FirstActivity", "firebaseAuth == null");
        }
    }
}
