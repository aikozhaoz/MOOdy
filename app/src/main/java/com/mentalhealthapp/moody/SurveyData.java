package com.mentalhealthapp.moody;


import java.util.HashMap;

public class SurveyData {
    private HashMap surveyData = new HashMap();

    public SurveyData(){

    }

    public SurveyData(HashMap surveyData){
        this.surveyData = surveyData;
    }

    public HashMap getSurveyData() { return surveyData; }

    public void setSurveyData(HashMap surveyData) {
        this.surveyData = surveyData;
    }
}