package com.example.searchbygenre;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserReg extends AppCompatActivity implements View.OnClickListener {
    private FirebaseAuth mAuth;
    private EditText etFullName, etEmail, etPassword;
    private TextView registerBtn;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_reg);

        mAuth = FirebaseAuth.getInstance();

        registerBtn = (Button) findViewById(R.id.registerBtn);
        registerBtn.setOnClickListener(this);

        etEmail = (EditText) findViewById(R.id.etEmailReg);
        etFullName =  (EditText) findViewById(R.id.etNameReg);
        etPassword = (EditText) findViewById(R.id.etPasswordReg);

        progressBar = (ProgressBar) findViewById(R.id.progressBar2);
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.registerBtn:
            registerUser();
            break;
        }
    }

    private void registerUser() {
        final String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        final String fullName= etFullName.getText().toString().trim();

        if(fullName.isEmpty()){
            etFullName.setError("Full Name is Required");
            etFullName.requestFocus();
            return;
        }
        if(email.isEmpty()){
            etEmail.setError("Full Name is Required");
            etEmail.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            etEmail.setError("Please Provide Valid Email");
            etEmail.requestFocus();
            return;
        }

        if(password.isEmpty()){
            etPassword.setError("Full Name is Required");
            etPassword.requestFocus();
            return;
        }

        if(password.length() < 6){
            etPassword.setError("Password too Short");
            etPassword.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    User user = new User(fullName, email);
                    FirebaseDatabase.getInstance().getReference("Users")
                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(UserReg.this, "User has been Registered", Toast.LENGTH_LONG ).show();
                                progressBar.setVisibility(View.GONE);

                                // Redirect User to Login Layout
                                Intent i = new Intent(UserReg.this, LoginActivity.class);
                                startActivity(i);

                                System.out.println("done");
                                Log.i("Complete", "onComplete: was registred");
                            }else{
                                Toast.makeText(UserReg.this, "Failed to Register! Try again", Toast.LENGTH_LONG).show();
                                progressBar.setVisibility(View.GONE);
                            }
                        }
                   });
                }else{
                    Toast.makeText(UserReg.this, "Failed to Register! Try again", Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.GONE);
                }
            }
        });
    }
}