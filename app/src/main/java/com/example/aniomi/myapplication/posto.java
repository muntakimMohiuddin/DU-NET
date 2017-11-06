package com.example.aniomi.myapplication;

/**
 * Created by aniomi on 10/8/17.
 */

public class posto {
    String id;
    String cnt;
    String t;
    String d;
    String sen;
    String description;
    String dept; // id

    //ASIF
    static String ttext;

    static int b[]=new int[100];

    public posto()
    {
        id=t=d=sen=description=dept="Omi";
        cnt="0";
    }

    public posto(String id , String cnt , String sen , String dept , String description)
    {
        this.id = id;
        this.cnt = cnt;
        this.sen = sen;
        this.dept= dept;
        this.description = description;
    }

    public posto(String id, String cnt, String t, String d, String sen, String description, String dept) {
        this.id = id;
        this.cnt = cnt;
        this.t = t;
        this.d = d;
        this.sen = sen;
        this.description = description;
        this.dept = dept;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCnt() {
        return cnt;
    }

    public void setCnt(String cnt) {
        this.cnt = cnt;
    }

    public String getT() {
        return t;
    }

    public void setT(String t) {
        this.t = t;
    }

    public String getD() {
        return d;
    }

    public void setD(String d) {
        this.d = d;
    }

    public String getSen() {
        return sen;
    }

    public void setSen(String sen) {
        this.sen = sen;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }
}
