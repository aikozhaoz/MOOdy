package com.mentalhealthapp.moody;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
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
 * Use the {@link SurveyFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SurveyFragment extends Fragment {
    final private String UID = FirebaseAuth.getInstance().getCurrentUser().getUid();
    private List<SurveyData> surveyDataList;
    private DatabaseReference ref;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SurveyFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BlankFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SurveyFragment newInstance(String param1, String param2) {
        SurveyFragment fragment = new SurveyFragment();
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
        return inflater.inflate(R.layout.fragment_survey, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        Button submitButton = getView().findViewById(R.id.submit);
        RadioGroup question1 = getView().findViewById(R.id.question1);
        RadioGroup question2 = getView().findViewById(R.id.question2);
        RadioGroup question3 = getView().findViewById(R.id.question3);
        RadioGroup question4 = getView().findViewById(R.id.question4);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        ref = database.getReference("SurveysTest/" + UID);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Object value = snapshot.getValue();
                if (value != null){
                    List result = (List) value;
                    updateSurveyDataList(result);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        surveyDataList = new ArrayList<>();

        question1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                // Get the selected Radio Button
                RadioButton radioButton = (RadioButton) radioGroup.findViewById(i);
            }
        });

        question2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                // Get the selected Radio Button
                RadioButton radioButton = (RadioButton) radioGroup.findViewById(i);
            }
        });

        question3.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                // Get the selected Radio Button
                RadioButton radioButton = (RadioButton) radioGroup.findViewById(i);
            }
        });

        question4.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                // Get the selected Radio Button
                RadioButton radioButton = (RadioButton) radioGroup.findViewById(i);
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int selectedId1 = question1.getCheckedRadioButtonId();
                int selectedId2 = question2.getCheckedRadioButtonId();
                int selectedId3 = question3.getCheckedRadioButtonId();
                int selectedId4 = question4.getCheckedRadioButtonId();

                //need to get the date
                SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
                Calendar c = Calendar.getInstance();
                String date = sdf.format(c.getTime());


                //need to check if they already submitted all questions
                if (selectedId1 == -1 || selectedId2 == -1 || selectedId3 == -1 || selectedId4 == -1) {
                    Toast.makeText(getView().getContext(), "Please answer all questions", Toast.LENGTH_SHORT).show();
                } else {
                    RadioButton answer1 = (RadioButton) question1.findViewById(selectedId1);
                    RadioButton answer2 = (RadioButton) question2.findViewById(selectedId2);
                    RadioButton answer3 = (RadioButton) question3.findViewById(selectedId3);
                    RadioButton answer4 = (RadioButton) question4.findViewById(selectedId4);


                    HashMap data = new HashMap();
                    data.put("Date", date);
                    data.put("Question1", answer1.getText());
                    data.put("Question2", answer2.getText());
                    data.put("Question3", answer3.getText());
                    data.put("Question4", answer4.getText());

                    SurveyData surveyData = new SurveyData(data);

                    updateDatabase(surveyData);
//
//                    surveyDataList.add(surveyData);
//                    Survey survey = new Survey(surveyDataList);
//
//                    ref.setValue(survey);
                    //now we put these in the database
//                    ref.child("Question1").setValue(answer1.getText());
//                    ref.child("Question2").setValue(answer2.getText());
//                    ref.child("Question3").setValue(answer3.getText());
//                    ref.child("Question4").setValue(answer4.getText());

                    // Now display survey has been submitted
                    Toast.makeText(getView().getContext(), "Survey Submitted", Toast.LENGTH_SHORT).show();
                    //clear all the radio buttons
                    question1.clearCheck();
                    question2.clearCheck();
                    question3.clearCheck();
                    question4.clearCheck();
                }
            }
        });
    }

    public void updateDatabase(SurveyData surveyData) {
        List<SurveyData> updateSurvey = new ArrayList<>();
        for (int i=0; i<surveyDataList.size(); i++){
            updateSurvey.add( surveyDataList.get(i));
        }
        updateSurvey.add(surveyData);

        ref.setValue(updateSurvey);
    }

    private void updateSurveyDataList(List<HashMap> valueList) {
        surveyDataList.clear();
        for (int i=0;i< valueList.size();i++){
            HashMap survey = valueList.get(i);
            SurveyData surveyData = new SurveyData((HashMap) survey.get("surveyData"));
            surveyDataList.add(surveyData);
        }
    }
}