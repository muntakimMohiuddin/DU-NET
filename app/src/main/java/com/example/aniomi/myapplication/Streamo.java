package com.example.aniomi.myapplication;

/**
 * Created by aniomi on 10/1/17.
 */

public class Streamo {
    public String getMai() {
        return mai;
    }

    public void setMai(String mai) {
        this.mai = mai;
    }

    Streamo()
    {
        mai=sen=rec=name="Flash";
    }
    public Streamo(String mai, String sen, String rec, String name) {
        this.mai = mai;
        this.sen = sen;
        this.rec = rec;
        this.name = name;
    }


    public String getSen() {
        return sen;

    }

    public void setSen(String sen) {
        this.sen = sen;
    }

    public String getRec() {
        return rec;
    }

    public void setRec(String rec) {
        this.rec = rec;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    String mai,sen,rec,name;

}
