package com.mentalhealthapp.moody;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UpdatePasswordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_password);
        EditText email = findViewById(R.id.et_email);
        EditText oldpassword = findViewById(R.id.et_old_password);
        EditText newpassword = findViewById(R.id.et_new_password);
        Button update = findViewById(R.id.update_password_button);
        update.setOnClickListener(v -> updateUser(email.getText().toString(), oldpassword.getText().toString(), newpassword.getText().toString()));
    }

    private void updateUser(String email, String oldpassword, String newpassword) {
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        // Get auth credentials from the user for re-authentication. The example below shows
        // email and password credentials but there are multiple possible providers,
        // such as GoogleAuthProvider or FacebookAuthProvider.
        AuthCredential credential = EmailAuthProvider.getCredential(email, oldpassword);
        // Prompt the user to re-provide their sign-in credentials
        user.reauthenticate(credential)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        user.updatePassword(newpassword).addOnCompleteListener(task1 -> {
                            if (task1.isSuccessful()) {
                                Toast.makeText(UpdatePasswordActivity.this, "Update password Successfully,", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(UpdatePasswordActivity.this, "Update password failed,", Toast.LENGTH_LONG).show();
                            }
                        });
                    } else {
                        Toast.makeText(UpdatePasswordActivity.this, "Authentication failed,", Toast.LENGTH_LONG).show();
                    }
                });
    }
}