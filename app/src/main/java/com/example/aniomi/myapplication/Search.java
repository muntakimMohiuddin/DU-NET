package com.example.aniomi.myapplication;

/**
 * Created by aniomi on 10/2/17.
 */

public class Search {
    static Search curr=new Search();
    String name;
    String dept;
    String year;
    public Search()
    {
        name=dept=year=blood=tut="none";
    }
    public String getName() {
        return name;
    }
    void  allset()
    {
        name=dept=year=blood=tut="none";
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getBlood() {
        return blood;
    }

    public void setBlood(String blood) {
        this.blood = blood;
    }

    public String getTut() {
        return tut;
    }

    public void setTut(String tut) {
        this.tut = tut;
    }

    String blood;
    String tut;
}
