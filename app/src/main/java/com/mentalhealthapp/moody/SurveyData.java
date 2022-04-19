package com.mentalhealthapp.moody;


import java.util.HashMap;

public class SurveyData {
    private final HashMap surveyData;

    public SurveyData(HashMap surveyData){
        this.surveyData = surveyData;
    }

    public HashMap getSurveyData() { return surveyData; }
}