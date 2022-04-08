package com.mentalhealthapp.moody;

import java.util.List;

public class GoalsData {
    private List<String> goals;

    public GoalsData(){

    }

    public GoalsData( List<String> goals){
        this.goals = goals;
    }
    public List<String> getGoals() { return goals; }

    public void setGoals(List<String> goals) {
        this.goals = goals;
    }
}