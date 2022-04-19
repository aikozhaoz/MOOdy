package com.mentalhealthapp.moody;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileFragment extends Fragment {
    FirebaseAuth auth;
    View profileView;
    private final String userName = HomeActivity.userName;
    TextView helloMessage;
    Button updatePasswordButton, deleteAccountButton, logOutButton;
    User currentUser;

    final private String UID = FirebaseAuth.getInstance().getCurrentUser().getUid();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        auth = FirebaseAuth.getInstance();
        profileView = view;
        helloMessage = profileView.findViewById(R.id.hello_message);
        updatePasswordButton = profileView.findViewById(R.id.update_password_button);
        deleteAccountButton = profileView.findViewById(R.id.delete_account_button);
        logOutButton = profileView.findViewById(R.id.log_out_button);
        String welcomeMessage = "Hello " + userName + "!";
        helloMessage.setText(welcomeMessage);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("Users/" + UID);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User value = snapshot.getValue(User.class);
                if (value != null){
                    currentUser = value;
                    setName();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        updatePasswordButton.setOnClickListener(view1 -> {
            Intent intent = new Intent(getActivity().getApplicationContext(), UpdatePasswordActivity.class);
            getActivity().startActivity(intent);
        });
        deleteAccountButton.setOnClickListener(view12 -> {
            Intent intent = new Intent(getActivity().getApplicationContext(), DeleteAccountActivity.class);
            getActivity().startActivity(intent);
        });
        logOutButton.setOnClickListener(view13 -> {
            Intent i = new Intent(getActivity().getApplicationContext(), MainActivity.class);
            i.putExtra("Log Out", true);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            auth.signOut();

            getActivity().startActivity(i);
            getActivity().finish();
        });
    }

    private void setName() {
        String name = currentUser.getFirstName();
        helloMessage.setText("Hello "+name+"!");
    }


}
