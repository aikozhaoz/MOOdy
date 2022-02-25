package com.mentalhealthapp.moody;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Button;
import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;

public class SignUpActivity extends AppCompatActivity {

    EditText etFirstName, etLastName, etEmail, etPassword, etConfirmPassword;
    final int MIN_PASSWORD_LENGTH = 8;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Create back button
        viewInitializations();
    }

    public void viewInitializations() {
        etFirstName = findViewById(R.id.et_first_name);
        etLastName = findViewById(R.id.et_last_name);
        etEmail = findViewById(R.id.et_email);
        etPassword = findViewById(R.id.et_password);
        etConfirmPassword = findViewById(R.id.et_confirm_password);


        // Fire sign up button
        Button signupButton = findViewById(R.id.signup_button_signup);
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // If Input is valid, send data to our server
                String firstName = etFirstName.getText().toString();
                String lastName = etLastName.getText().toString();
                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();
                String confirmPassword = etConfirmPassword.getText().toString();

                if (TextUtils.isEmpty(firstName)) {
                    etFirstName.setError("Empty Input! Please enter your first name.");
                    etFirstName.requestFocus();
                } else if (TextUtils.isEmpty(lastName)) {
                    etLastName.setError("Empty Input! Please enter your last name.");
                    etLastName.requestFocus();
                } else if (TextUtils.isEmpty(email)) {
                    etEmail.setError("Empty Input! Please enter your email.");
                    etEmail.requestFocus();
                } else if (TextUtils.isEmpty(password)) {
                    etPassword.setError("Empty Input! Please enter your password.");
                    etPassword.requestFocus();
                } else if (TextUtils.isEmpty(confirmPassword)) {
                    etConfirmPassword.setError("Empty Input! Please confirm your password.");
                    etConfirmPassword.requestFocus();
                }
                // Check email format
                else if (!isEmailValid(email)) {
                    etEmail.setError("Please enter valid Email address.");
                    etEmail.requestFocus();
                }
                // Check minimum password Length
                else if (password.length() < MIN_PASSWORD_LENGTH) {
                    etPassword.setError("Password invalid! Password length must be more than " + MIN_PASSWORD_LENGTH + "characters");
                    etPassword.requestFocus();
                }
                // Check if password matches confirm password
                else if (!password.equals(confirmPassword)) {
                    etConfirmPassword.setError("Password does not match.");
                    etPassword.requestFocus();
                }else{
                    // Sign user up with firebase
                    signUserUp(firstName, lastName, email, password);
                }
            }
        });
    }

    public boolean isEmailValid(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }


    //    Sign user up with given credentials
    private void signUserUp(String firstName, String lastName, String email, String password) {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = auth.getCurrentUser();
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                // Note to team:
                // A toast provides simple feedback about an operation in a small popup.
                if (task.isSuccessful()) {
                    // Send user verification email
                    currentUser.sendEmailVerification();
                    Toast.makeText(SignUpActivity.this, "Sign Up Success", Toast.LENGTH_SHORT).show();
                    // To do: Redirect to user's home page
                    // finish();
                    // Check if user is verified.
                    if(currentUser.isEmailVerified()){
                        Toast.makeText(SignUpActivity.this, "You are verified!", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(SignUpActivity.this, "Failed to send verification email. User already existed.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}