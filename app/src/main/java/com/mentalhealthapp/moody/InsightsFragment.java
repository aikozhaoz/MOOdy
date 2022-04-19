package com.mentalhealthapp.moody;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class InsightsFragment extends Fragment {

    private List<Journal> journalList;

    private List<SurveyData> surveyDataList;

    public InsightsFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_insights, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        surveyDataList = new ArrayList<>();
        journalList = new ArrayList<>();

        DatabaseReference databaseReference = database.getReference("Surveys/" + user.getUid());
        databaseReference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Object value = snapshot.getValue();
                if (value != null){
                    List result = (List) value;
                    updateSurveyDataList(result);
                    generateSurveyInsights();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        DatabaseReference journalReference = FirebaseDatabase.getInstance().getReference("Journals/" + user.getUid());
        journalReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<HashMap> value = (List) snapshot.getValue();
                if (value != null){
                    updateJournalList(value);
                }
                generateJournalInsights();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void generateJournalInsights() {
        int entriesPerWeek=0;
        int entriesPerMonth=0;
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
        Calendar c = Calendar.getInstance();
        String currentDate = sdf.format(c.getTime());
        int currentDay = Integer.parseInt(currentDate.substring(3,5));
        int currentMonth = Integer.parseInt(currentDate.substring(0,2));

        for (int i=0; i<journalList.size(); i++){
            Journal entry = journalList.get(i);
            String date = entry.getDate();
            int day = Integer.parseInt(date.substring(3,5));
            int month = Integer.parseInt(date.substring(0,2));
            if (month == currentMonth){
                entriesPerMonth++;
            }
            if ((currentDay-7) < day){
                entriesPerWeek++;
            }
        }

        TextView entriesPerWeekText = getView().findViewById(R.id.entriesPerWeek);
        entriesPerWeekText.setText(entriesPerWeek+"");

        TextView entriesPerMonthText = getView().findViewById(R.id.entriesPerMonth);
        entriesPerMonthText.setText(entriesPerMonth+"");
    }

    private void generateSurveyInsights() {
        int averageMoodWeek = 0;
        int entriesInWeek = 0;
        int entriesInMonth = 0;
        int averageMoodMonth = 0;
        String moodWeek = "";
        String moodMonth = "";
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
        Calendar c = Calendar.getInstance();
        String currentDate = sdf.format(c.getTime());
        int currentDay = Integer.parseInt(currentDate.substring(3,5));
        int currentMonth = Integer.parseInt(currentDate.substring(0,2));
        int currentYear = Integer.parseInt(currentDate.substring(6));

        for (int i=0; i<surveyDataList.size();i++){
            SurveyData survey = surveyDataList.get(i);
            HashMap data = survey.getSurveyData();
            String date = (String) data.get("Date");
            int day = Integer.parseInt(date.substring(3,5));
            int month = Integer.parseInt(date.substring(0,2));
            int year = Integer.parseInt(date.substring(6));
            if (currentMonth == month && currentYear == year) {
                int currentMood = 0;
                entriesInWeek++;
                entriesInMonth++;

                String q1 = data.get("Question1").toString();
                String q2 = data.get("Question2").toString();
                String q3 = data.get("Question3").toString();
                String q4 = data.get("Question4").toString();
                switch (q1) {
                    case "Awful":
                        currentMood += 1;
                        break;
                    case "Bad":
                        currentMood += 2;
                        break;
                    case "Neutral":
                        currentMood += 3;
                        break;
                    case "Good":
                        currentMood += 4;
                        break;
                    case "Amazing":
                        currentMood += 5;
                        break;
                }

                switch (q2) {
                    case "Awful":
                        currentMood += 1;
                        break;
                    case "Bad":
                        currentMood += 2;
                        break;
                    case "Neutral":
                        currentMood += 3;
                        break;
                    case "Good":
                        currentMood += 4;
                        break;
                    case "Amazing":
                        currentMood += 5;
                        break;
                }

                switch (q3) {
                    case "A lot":
                        currentMood += 1;
                        break;
                    case "Some":
                        currentMood += 2;
                        break;
                    case "Neutral":
                        currentMood += 3;
                        break;
                    case "Little":
                        currentMood += 4;
                        break;
                    case "None":
                        currentMood += 5;
                        break;
                }

                switch (q4) {
                    case "None":
                        currentMood += 1;
                        break;
                    case "1 Hour":
                        currentMood += 2;
                        break;
                    case "2 Hours":
                        currentMood += 3;
                        break;
                    case "3 Hours":
                        currentMood += 4;
                        break;
                    case "4+ Hours":
                        currentMood += 5;
                        break;
                }

                currentMood = currentMood / 4;
                if ((currentDay - 7) < day) {
                    averageMoodWeek += currentMood;
                }
                averageMoodMonth += currentMood;
            }

            averageMoodMonth /= entriesInMonth;
            averageMoodWeek /= entriesInWeek;
            if (averageMoodWeek == 1){
                moodWeek = "Awful";
            } else if (averageMoodWeek == 2){
                moodWeek = "Bad";
            } else if (averageMoodWeek == 3){
                moodWeek = "Neutral";
            } else if (averageMoodWeek == 4){
                moodWeek = "Good";
            } else if (averageMoodWeek == 5){
                moodWeek = "Amazing";
            }

            if (averageMoodMonth == 1){
                moodMonth = "Awful";
            } else if (averageMoodMonth == 2){
                moodMonth = "Bad";
            } else if (averageMoodMonth == 3){
                moodMonth = "Neutral";
            } else if (averageMoodMonth == 4){
                moodMonth = "Good";
            } else if (averageMoodMonth == 5) {
                moodMonth = "Amazing";
            }
            TextView averageMoodWeekText = getView().findViewById(R.id.averageMoodWeek);
            averageMoodWeekText.setText(moodWeek);

            TextView averageMoodMonthText = getView().findViewById(R.id.averageMoodMonth);
            averageMoodMonthText.setText(moodMonth);


                //calculate average mood for day and add to average for week

            }
    }

    private void updateSurveyDataList(List<HashMap> valueList) {
        surveyDataList.clear();
        for (int i=0;i< valueList.size();i++){
            HashMap survey = valueList.get(i);
            SurveyData surveyData = new SurveyData((HashMap) survey.get("surveyData"));
            surveyDataList.add(surveyData);
        }

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