package com.example.ali.tutorial_gettingstarted;

import java.util.Date;

/**
 * Created by Ali on 31.05.2015.
 */
public class Note {
    private String title;
    private String text;
    private Date date;

    public Note(String title, String text) {
        this.title = title;
        this.text = text;
        this.date = new Date();
    }
    //TODO: auf anfang und ende von titel achten!!!
    public Note(String text) {
        this.title = text.substring(0,50)
        this.text = text;
        this.date=new Date();
    }

    public void setText(String text) {
        this.text = text;
        this.date = new Date();
    }

    public void setTitle(String title) {
        this.title = title;
        this.date = new Date();
    }

    @Override
    public String toString() {
        return text;
    }
}
