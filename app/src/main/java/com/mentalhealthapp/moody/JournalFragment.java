package com.mentalhealthapp.moody;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
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
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link JournalFragment#} factory method to
 * create an instance of this fragment.
 */
public class JournalFragment extends Fragment {
    private DatabaseReference db;
    private String userID;
    List<Journal> journalList;
    View journalFragmentView;
    Boolean journalFilled = false;
    EditText etJournalTitle, etJournalText;
    Journal journal;
    ImageView previewImg;
    Button uploadJournalPicture, reviewJournals, submitButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_journal, container, false);
    }

//    ActivityResultLauncher<Intent> launchSelectImage = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
//        @Override
//        public void onActivityResult(Uri result) {
//
//        }
//    });
//
//    public void selectImage(){
//        Intent iGallery = new Intent(Intent.ACTION_PICK);
//        iGallery.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//        startActivityForResult(iGallery, GALLERY_REQ_CODE);
//        selectImageIntent.setType("image/*");
//        selectImageIntent.setAction(Intent.ACTION_GET_CONTENT);
//        startActivityForResult(Intent.createChooser(selectImageIntent, "Upload Image"), 200);
//    }

    public void createJournal(String journalTitle, String journalText){
        journal = new Journal();
        journal.setJournalTitle(journalTitle);
        journal.setJournalText(journalText);
    }

    public void writeJournalDB(){

    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        // Initialize firebase database
        userID = FirebaseAuth.getInstance().getUid();
        db = FirebaseDatabase.getInstance().getReference("JournalTest/" + userID);
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<HashMap> value = (List) snapshot.getValue();
                if (value != null){
                    updateJournalList(value);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        // Get current view
        journalFragmentView = view;

        // Get journal title and text
        etJournalTitle = journalFragmentView.findViewById(R.id.etjournal_title);
        etJournalText = journalFragmentView.findViewById(R.id.etjournal_text);

        // Initialize all the buttons
        reviewJournals = (Button) journalFragmentView.findViewById(R.id.review_journals);
        submitButton = (Button) journalFragmentView.findViewById(R.id.submit_journal);
        journalList = new ArrayList<>();

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String journalTitle = etJournalTitle.getText().toString();
                String journalText = etJournalText.getText().toString();
                if (!(journalTitle.equals("") && journalText.equals(""))) {
                    Journal journal = new Journal(journalTitle,journalText);
                    updateDatabase(journal);
                    Toast.makeText(getView().getContext(), "Journal submitted", Toast.LENGTH_SHORT).show();
                    etJournalText.setText("");
                    etJournalTitle.setText("");
                } else {
                    Toast.makeText(getView().getContext(), "Please enter a title and description", Toast.LENGTH_SHORT).show();
                }
            }
        } );
    }

    private void updateDatabase(Journal journal) {
        List<Journal> updateJournal = new ArrayList<>();
        for (int i=0; i<journalList.size(); i++){
            updateJournal.add(journalList.get(i));
        }
        updateJournal.add(journal);

        db.setValue(updateJournal);
    }

    private void updateJournalList(List<HashMap> journalList) {
        this.journalList.clear();
        for (int i=0;i< journalList.size();i++){
            HashMap journal = journalList.get(i);
            Journal newJournal = new Journal();

            newJournal.setJournalTitle(journal.get("journalTitle").toString());
            newJournal.setJournalText(journal.get("journalText").toString());
            newJournal.setDate(journal.get("date").toString());

            this.journalList.add(newJournal);
        }
    }
}















