package com.mentalhealthapp.moody;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Journals extends AppCompatActivity {

    private DatabaseReference db;
    private String userID;
    List<Journal> journalList;
    private ArrayList<String> items;
    private ArrayAdapter<String> itemsAdapter;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journals);
        userID = FirebaseAuth.getInstance().getUid();
        db = FirebaseDatabase.getInstance().getReference("Journals/" + userID);
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

        journalList = new ArrayList<>();
        listView = findViewById(R.id.journalsView);
        items = new ArrayList<>();
        itemsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);
        listView.setAdapter(itemsAdapter);

        //setUpListViewListener();

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

        for (int i=0; i< this.journalList.size();i++){
            Journal entry = this.journalList.get(i);
            String date = entry.getDate();
            String title = entry.getJournalTitle();
            String text = entry.journalText;
            String line = title + " "+date+"\n"+text;
            items.add(line);
        }

        itemsAdapter.notifyDataSetChanged();

    }
}