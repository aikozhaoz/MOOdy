package com.mentalhealthapp.moody;

import java.util.List;

public class GoalsData {
    private final List<String> goals;


    public GoalsData( List<String> goals){
        this.goals = goals;
    }
    public List<String> getGoals() { return goals; }
}