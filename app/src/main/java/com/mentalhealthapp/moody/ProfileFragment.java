package com.mentalhealthapp.moody;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ProfileFragment extends Fragment {
    FirebaseAuth auth;
    View profileView;
    private String userName = HomeActivity.userName;
    TextView helloMessage;
    ImageView profilePic;
    Button takePicButton, updatePasswordButton, deleteAccountButton, logOutButton;
    final String UID = FirebaseAuth.getInstance().getCurrentUser().getUid();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        auth = FirebaseAuth.getInstance();
        profileView = view;
        helloMessage = (TextView) profileView.findViewById(R.id.hello_message);
        takePicButton = (Button) profileView.findViewById(R.id.take_picture_button);
        updatePasswordButton = (Button) profileView.findViewById(R.id.update_password_button);
        deleteAccountButton = (Button) profileView.findViewById(R.id.delete_account_button);
        logOutButton = (Button) profileView.findViewById(R.id.log_out_button);
        String welcomeMessage = "Hello " + userName + " !";
        helloMessage.setText(welcomeMessage);
        updatePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity().getApplicationContext(), UpdatePasswordActivity.class);
                getActivity().startActivity(intent);
            }
        });
        deleteAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity().getApplicationContext(), DeleteAccountActivity.class);
                getActivity().startActivity(intent);
            }
        });
        logOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity().getApplicationContext(), MainActivity.class);
                i.putExtra("Log Out", true);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                getActivity().startActivity(i);
                auth.signOut();
                getActivity().finish();
            }
        });
    }
}