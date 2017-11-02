package com.example.aniomi.myapplication;

/**
 * Created by aniomi on 11/1/17.
 */

public class comment {
    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDatem() {
        return datem;
    }

    public void setDatem(String datem) {
        this.datem = datem;
    }

    public String getTimem() {
        return timem;
    }

    public void setTimem(String timem) {
        this.timem = timem;
    }

    public comment(String sender, String name, String description, String datem, String timem) {
        this.sender = sender;
        this.name = name;
        this.description = description;
        this.datem = datem;
        this.timem = timem;
    }

    String sender;

    public comment() {
        this.sender = "omi";
        this.name = "omi";
        this.description = "omi";
        this.datem = "omi";
        this.timem = "omi";
    }

    String name;
    String description;
    String datem;
    String timem;
}
