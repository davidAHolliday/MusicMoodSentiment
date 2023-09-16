package com.psych.demo.model;

import java.util.HashMap;

public class MoodResponse{

    String Mood;
    HashMap<String, String> data;

    public String getMood() {
        return Mood;
    }

    public void setMood(String mood) {
        Mood = mood;
    }

    public  HashMap<String, String> getData() {
        return data;
    }

    public void setData( HashMap<String, String> data) {
        this.data = data;
    }
}