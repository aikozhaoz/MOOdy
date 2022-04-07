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
import android.content.Intent;
import android.view.MenuItem;
import androidx.appcompat.app.ActionBar;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
            }
        });

        // Fire sign up activity
        Button signupButton = findViewById(R.id.main_button_signUp);
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
                startActivity(intent);
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