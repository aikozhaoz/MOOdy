package com.mentalhealthapp.moody;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Journal {
    String date;
    String journalTitle;
    String journalText;


    public Journal(){

    }

    public Journal(String journalTitle, String journalText) {
        this.journalText = journalText;
        this.journalTitle = journalTitle;

        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
        Calendar c = Calendar.getInstance();
        this.date = sdf.format(c.getTime());
    }


    public void setJournalTitle(String journalTitle){
        this.journalTitle = journalTitle;
    }

    public String getJournalTitle(){
        return this.journalTitle;
    }

    public void setJournalText(String journalText){
        this.journalText = journalText;
    }

    public void setDate(String date){
        this.date = date;
    }

    public String getDate(){
        return this.date;
    }
}