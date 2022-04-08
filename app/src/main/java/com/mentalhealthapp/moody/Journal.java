package com.mentalhealthapp.moody;

import java.time.LocalDateTime;

public class Journal {
    LocalDateTime datetime;
    String journalTitle;
    String journalText;


    public Journal(){
        this.datetime = LocalDateTime.now();
    }

    public void setJournalTitle(String journalTitle){
        this.journalTitle = journalTitle;
    }

    public void setJournalText(String journalText){
        this.journalText = journalText;
    }
}
