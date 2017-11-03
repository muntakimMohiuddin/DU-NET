package com.example.aniomi.myapplication;

/**
 * Created by asif on 11/3/17.
 */

public class Group_details {
    String name , about ;
    Students operner;
    Group_details(String name , String about , Students operner)
    {
        this.name = name;
        this.about = about;
        this.operner = operner;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public Students getOperner() {
        return operner;
    }

    public void setOperner(Students operner) {
        this.operner = operner;
    }
}
