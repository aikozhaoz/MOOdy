package com.mentalhealthapp.moody;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class SelfAssessmentActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_self_assessment);
        Button submitButton = findViewById(R.id.submit);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SelfAssessmentActivity.this, UpdatePasswordActivity.class);
                startActivity(intent);
            }
        });
        Button deleteButton = findViewById(R.id.delete_account_button);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SelfAssessmentActivity.this, DeleteAccountActivity.class);
                startActivity(intent);
            }
        });
    }
}
