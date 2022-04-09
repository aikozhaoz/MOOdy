package com.mentalhealthapp.moody;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link InsightsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InsightsFragment extends Fragment {


    private ArrayList<String> items;
    private ArrayAdapter<String> itemsAdapter;
    private ListView listView;
    private Button button;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private DatabaseReference journalReference;
    private EditText goalText;
    private FirebaseUser user;
    private List<Journal> journalList;

    private List<SurveyData> surveyDataList;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public InsightsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment InsightsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static InsightsFragment newInstance(String param1, String param2) {
        InsightsFragment fragment = new InsightsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_insights, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        user = FirebaseAuth.getInstance().getCurrentUser();
        database = FirebaseDatabase.getInstance();
        surveyDataList = new ArrayList<>();
        journalList = new ArrayList<>();

        databaseReference = database.getReference("SurveysTest/"+user.getUid());
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

        journalReference = FirebaseDatabase.getInstance().getReference("JournalTest/" + user.getUid());
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
        String uid = user.getUid();
        //DatabaseReference ref = database.getReference();

        //ref.orderByChild("email").equalTo(email);
        //ref = database.getReference("Users" + user.getFirstName() + user.getLastName());
//        input = getView().findViewById(R.id.editGoalText);
//        listView = view.findViewById(R.id.goalsView);
//        goalText = (EditText) view.findViewById(R.id.editGoalText);

//        items = new ArrayList<>();
//        itemsAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, items);
//        listView.setAdapter(itemsAdapter);


    }

    private void generateJournalInsights() {
        int entriesPerWeek=0;
        int entriesPerMonth=0;
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
        Calendar c = Calendar.getInstance();
        String currentDate = sdf.format(c.getTime());
        int currentDay = Integer.parseInt(currentDate.substring(3,5));
        int currentMonth = Integer.parseInt(currentDate.substring(0,2));
        int currentYear = Integer.parseInt(currentDate.substring(6));

        for (int i=0; i<journalList.size(); i++){
            Journal entry = journalList.get(i);
            String date = entry.getDate();
            int day = Integer.parseInt(date.substring(3,5));
            int month = Integer.parseInt(date.substring(0,2));
            int year = Integer.parseInt(date.substring(6));
            if (month == currentMonth){
                entriesPerMonth++;
            }
            if ((currentDay-7) < day){
                entriesPerWeek++;
            }
        }

        TextView entriesPerWeekText = (TextView) getView().findViewById(R.id.entriesPerWeek);
        entriesPerWeekText.setText(entriesPerWeek+"");

        TextView entriesPerMonthText = (TextView) getView().findViewById(R.id.entriesPerMonth);
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
            if (currentMonth == currentMonth) {
                int currentMood = 0;
                entriesInWeek++;
                entriesInMonth++;

                String q1 = data.get("Question1").toString();
                String q2 = data.get("Question2").toString();
                String q3 = data.get("Question3").toString();
                String q4 = data.get("Question4").toString();
                if (q1.equals("Awful")) {
                    currentMood += 1;
                } else if (q1.equals("Bad")) {
                    currentMood += 2;
                } else if (q1.equals("Neutral")) {
                    currentMood += 3;
                } else if (q1.equals("Good")) {
                    currentMood += 4;
                } else if (q1.equals("Amazing")) {
                    currentMood += 5;
                }

                if (q2.equals("Awful")) {
                    currentMood += 1;
                } else if (q2.equals("Bad")) {
                    currentMood += 2;
                } else if (q2.equals("Neutral")) {
                    currentMood += 3;
                } else if (q2.equals("Good")) {
                    currentMood += 4;
                } else if (q2.equals("Amazing")) {
                    currentMood += 5;
                }

                if (q3.equals("A lot")) {
                    currentMood += 1;
                } else if (q1.equals("Some")) {
                    currentMood += 2;
                } else if (q1.equals("Neutral")) {
                    currentMood += 3;
                } else if (q1.equals("Little")) {
                    currentMood += 4;
                } else if (q1.equals("None")) {
                    currentMood += 5;
                }

                if (q4.equals("None")) {
                    currentMood += 1;
                } else if (q4.equals("1 Hour")) {
                    currentMood += 2;
                } else if (q4.equals("2 Hours")) {
                    currentMood += 3;
                } else if (q4.equals("3 Hours")) {
                    currentMood += 4;
                } else if (q4.equals("4+ Hours")) {
                    currentMood += 5;
                }

                currentMood = currentMood / 4;
                if ((currentDay - 7) < day && currentMonth == currentMonth) {
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
            TextView averageMoodWeekText = (TextView) getView().findViewById(R.id.averageMoodWeek);
            averageMoodWeekText.setText(moodWeek);

            TextView averageMoodMonthText = (TextView) getView().findViewById(R.id.averageMoodMonth);
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