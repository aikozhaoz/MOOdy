package com.mentalhealthapp.moody;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.FirebaseAuth;

public class LogInActivity extends AppCompatActivity {
    private static final String TAG = LogInActivity.class.getSimpleName();
    private FirebaseAuth auth;
    EditText etEmail, etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        getSupportActionBar().setTitle("Log In");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance();
    }


    @Override
    public void onResume() {
        super.onResume();
        validateUserCredentials();

    }

    @Override
    public void onBackPressed() {
        finish();
        startActivity(new Intent(getApplicationContext(),MainActivity.class));
    }

    public void validateUserCredentials() {
        etEmail = findViewById(R.id.et_email);
        etPassword = findViewById(R.id.et_password);
        // Fire log in button
        Button loginButton = findViewById(R.id.login_button_login);
        loginButton.setOnClickListener(view -> {
            // If Input is valid, validate user log in credentials with firebase
            String email = etEmail.getText().toString();
            String password = etPassword.getText().toString();
            // Check email format
            if (!isEmailValid(email)) {
                etEmail.setError("Please enter a valid Email address.");
                etEmail.requestFocus();
            }
            else{
                signIn(email, password);

                finish();
                startActivity(new Intent(getApplicationContext(), HomeActivity.class));
            }
        });
    }

    public boolean isEmailValid(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    // Sign user in with validated credentials
    private void signIn(String email, String password){
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, (OnCompleteListener) task -> {
            if(task.isSuccessful()){
                // Sign in success, update UI with the signed-in user's information
                Toast.makeText(LogInActivity.this, "Log In Success", Toast.LENGTH_SHORT).show();
            }else{
                // If sign in fails, display a message to the user.
                Log.d(TAG, "Log in:failure", task.getException());
                Toast.makeText(LogInActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
            }
        });
    }

}