package com.mentalhealthapp.moody;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Button;
import android.util.Log;
import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {

    private static final String TAG = SignUpActivity.class.getSimpleName();
    private final int MIN_PASSWORD_LENGTH = 8;
    private FirebaseAuth auth;
    private FirebaseUser currentUser;
    EditText etFirstName, etLastName, etEmail, etPassword, etConfirmPassword;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref = database.getReference("Users");
    User user;

    private static final String[] CAMERA_PERMISSION = new String[]{Manifest.permission.CAMERA};
    private static final int CAMERA_REQUEST_CODE = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        getSupportActionBar().setTitle("Sign Up");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance();
        // Initialize Firebase current user
        currentUser = auth.getCurrentUser();
        // Get user's info and check if they meet our criteria.
        validateUserCredentials();
        Button enableCamera = findViewById(R.id.take_profile_pic);
        enableCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hasCameraPermission()) {
                    enableCamera();
                } else {
                    requestPermission();
                }
            }
        });

    }

    private boolean hasCameraPermission() {
        return ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(
                this,
                CAMERA_PERMISSION,
                CAMERA_REQUEST_CODE
        );
    }

    private void enableCamera() {
        Intent intent = new Intent(this, CameraActivity.class);
        startActivity(intent);
    }

    public void validateUserCredentials() {
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
                    etEmail.setError("Please enter a valid Email address.");
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
                } else {
                    // Create an account for the user using firebase
                    createAccount(firstName, lastName, email, password);
                    Intent intent = new Intent(SignUpActivity.this, HomeActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    public boolean isEmailValid(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }


    //    Create an account for the user with given credentials
    private void createAccount(String firstName, String lastName, String email, String password) {
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
                    if (currentUser.isEmailVerified()) {
                        Toast.makeText(SignUpActivity.this, "You are verified!", Toast.LENGTH_SHORT).show();
                    }
                    //create user here
                    final String UID = FirebaseAuth.getInstance().getCurrentUser().getUid();
                    user = new User();
                    user.setEmail(email);
                    user.setFirstName(firstName);
                    user.setLastName(lastName);
                    ref.child(UID).setValue(user);
                } else {
                    Log.d(TAG, "Sign up: failure", task.getException());
                    Toast.makeText(SignUpActivity.this, "Failed to send verification email. User already existed.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}