package com.mentalhealthapp.moody;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link JournalFragment#} factory method to
 * create an instance of this fragment.
 */
public class JournalFragment extends Fragment {
    private DatabaseReference db;
    private String userID;
    View journalFragmentView;
    EditText etJournalTitle, etJournalText;
    Journal journal;
    Button reviewJournals, submitButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_journal, container, false);
    }

    public void createJournal(String journalTitle, String journalText){
        journal = new Journal();
        journal.setJournalTitle(journalTitle);
        journal.setJournalText(journalText);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        // Initialize firebase database
        userID = FirebaseAuth.getInstance().getUid();
        db = FirebaseDatabase.getInstance().getReference();

        // Get current view
        journalFragmentView = view;

        // Get journal title and text
        etJournalTitle = journalFragmentView.findViewById(R.id.etjournal_title);
        etJournalText = journalFragmentView.findViewById(R.id.etjournal_text);

        // Initialize all the buttons
        reviewJournals = (Button) journalFragmentView.findViewById(R.id.review_journals);
        submitButton = (Button) journalFragmentView.findViewById(R.id.submit_journal);


        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int i = 2;
                String journalTitle = etJournalTitle.getText().toString();
                String journalText = etJournalText.getText().toString();
                createJournal(journalTitle,journalText);
            }
        } );
    }
}