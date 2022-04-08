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
    private EditText goalText;
    private FirebaseUser user;

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

        databaseReference = database.getReference("SurveysTest/"+user.getUid());
        databaseReference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Object value = snapshot.getValue();
                if (value != null){
                    List result = (List) value;
                    updateSurveyDataList(result);
                    generateInsights();
                }

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

    private void generateInsights() {
        int averageMoodWeek = 0;
        int averageMoodMonth = 0;
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
            if (currentDay > (day-7) && currentMonth == currentMonth) {
                //calculate average mood for day and add to average for weed

            }
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
}