package com.mentalhealthapp.moody;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        if (user != null) {
            Intent intent = new Intent(MainActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
        }

        setContentView(R.layout.activity_main);
        // calling the action bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("MOOdy");
        // showing the back button in action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Fire log in activity
        Button loginButton = findViewById(R.id.main_button_logIn);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, LogInActivity.class);
                startActivity(intent);
                finish();
            }
        });

        // Fire sign up activity
        Button signupButton = findViewById(R.id.main_button_signUp);
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        setContentView(R.layout.activity_main);
        // calling the action bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("MOOdy");
        // showing the back button in action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Fire log in activity
        Button loginButton = findViewById(R.id.main_button_logIn);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, LogInActivity.class);
                startActivity(intent);
                finish();
            }
        });

        // Fire sign up activity
        Button signupButton = findViewById(R.id.main_button_signUp);
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

//    @Override
//    public void onBackPressed(){
//        super.onBackPressed();
//        Intent intent = new Intent(HomeActivity.this, TargetAc)
//    }

//    public void LogInActivity(View view) {
//        Intent intent = new Intent(this, LogInActivity.class);
//        startActivity(intent);
//    }
//
//    public void SignUpActivity(View view) {
//        Intent intent = new Intent(this, SignUpActivity.class);
//        startActivity(intent);
//    }
}