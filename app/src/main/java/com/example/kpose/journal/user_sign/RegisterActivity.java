package com.example.kpose.journal.user_sign;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.kpose.journal.MainActivity;
import com.example.kpose.journal.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {
    private Button btnReg;
    private TextInputLayout inName, inEmail, inPass;

    private FirebaseAuth firebaseAuth;
    private DatabaseReference fUsersDatabase;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        btnReg = findViewById(R.id.btn_reg);
        inName = findViewById(R.id.input_reg_name);
        inEmail = findViewById(R.id.input_reg_email);
        inPass = findViewById(R.id.input_reg_pass);

        firebaseAuth = FirebaseAuth.getInstance();
        fUsersDatabase = FirebaseDatabase.getInstance().getReference().child("Users");

        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uname = inName.getEditText().getText().toString().trim();
                String uemail = inEmail.getEditText().getText().toString().trim();
                String upass = inPass.getEditText().getText().toString().trim();

                registeruser(uname, uemail, upass);
            }
        });

    }

    private void registeruser(final String name, String email, String password){

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("please wait, processing your request...");

        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {

                            fUsersDatabase.child(firebaseAuth.getCurrentUser().getUid())
                                    .child("basic").child("name").setValue(name)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {

                                            if (task.isSuccessful()) {

                                                progressDialog.dismiss();

                                                Intent mainIntent = new Intent(RegisterActivity.this, MainActivity.class);
                                                startActivity(mainIntent);
                                                finish();
                                                Toast.makeText(RegisterActivity.this, "Account Created Successfully", Toast.LENGTH_SHORT).show();

                                            }else {
                                                progressDialog.dismiss();
                                                Toast.makeText(RegisterActivity.this, "ERROR : " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });

                        } else {

                            progressDialog.dismiss();
                            Toast.makeText(RegisterActivity.this, "ERROR! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }
}

