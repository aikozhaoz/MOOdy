package com.mentalhealthapp.moody;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

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

import java.util.ArrayList;
import java.util.List;

public class GoalsFragment extends Fragment {

    private ArrayList<String> items;
    private ArrayAdapter<String> itemsAdapter;
    private ListView listView;
    private DatabaseReference databaseReference;

    private EditText input;
    public GoalsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("Goals/"+ user.getUid());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                GoalsData value = snapshot.getValue(GoalsData.class);
                if (value != null){
                    List<String> valueList = value.getGoals();
                    updateGoalList(valueList);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        input = getView().findViewById(R.id.editGoalText);
        listView = view.findViewById(R.id.goalsView);
        Button button = view.findViewById(R.id.addGoalButton);
        EditText goalText = view.findViewById(R.id.editGoalText);
        goalText.setOnClickListener(view1 -> {
            if (input.getText().equals("Enter Goal")){
                input.getText().clear();
            }
        });

        button.setOnClickListener(this::addItem);

        items = new ArrayList<>();
        itemsAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, items);
        listView.setAdapter(itemsAdapter);




        setUpListViewListener();


    }

    private void setUpListViewListener() {
        listView.setOnItemLongClickListener((adapterView, view, i, l) -> {
            Context context = getActivity().getApplicationContext();
            Toast.makeText(context, "Goal Removed", Toast.LENGTH_LONG).show();

            items.remove(i);
            itemsAdapter.notifyDataSetChanged();

            updateDatabase();

            return true;
        });
    }

    private void addItem(View view) {
        String itemText = input.getText().toString();

        if(!(itemText.equals(""))){
            itemsAdapter.add(itemText);
            input.setText("");
        } else {
            Toast.makeText(getActivity().getApplicationContext(), "Please enter text...", Toast.LENGTH_LONG).show();
        }

        updateDatabase();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_goals, container, false);
    }

    public void updateDatabase() {
        List<String> goalList = new ArrayList<>();
        for (int i=0; i<itemsAdapter.getCount(); i++){
            goalList.add(itemsAdapter.getItem(i));
        }

        GoalsData goalData = new GoalsData(goalList);
        databaseReference.setValue(goalData);
    }

    public void updateGoalList(List<String> goalList){
        itemsAdapter.clear();
        for (int i=0; i< goalList.size(); i++){
            itemsAdapter.add(goalList.get(i));
        }
    }
}