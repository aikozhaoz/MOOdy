package com.mentalhealthapp.moody;

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

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        // Fire log in activity
//        Button loginButton = findViewById(R.id.main_button_logIn);
//        loginButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(MainActivity.this, LogInActivity.class);
//                startActivity(intent);
//            }
//        });

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