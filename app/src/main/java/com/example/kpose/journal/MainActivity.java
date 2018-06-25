package com.example.kpose.journal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth = FirebaseAuth.getInstance();

        updateUI();
    }

    private void updateUI() {
        if (firebaseAuth.getCurrentUser() != null) {
            Log.i("MainActivity", "firebaseAuth:= null");
        }else {
            Intent startIntent = new Intent(MainActivity.this, FirstActivity.class);
            startActivity(startIntent);
            finish();
            Log.i("MainActivity", "firebaseAuth == null");
        }
    }
}
