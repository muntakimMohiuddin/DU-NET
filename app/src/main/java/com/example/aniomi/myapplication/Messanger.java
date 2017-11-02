package com.example.aniomi.myapplication;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by aniomi on 10/20/17.
 */

public class Messanger {
    String sender;

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getDate_() {
        return date_;
    }

    public void setDate_(String date_) {
        this.date_ = date_;
    }

    public String getTime_() {
        return time_;
    }

    public void setTime_(String time_) {
        this.time_ = time_;
    }

    public String getSender_name() {
        return sender_name;
    }

    public void setSender_name(String sender_name) {
        this.sender_name = sender_name;
    }

    String type;
    String mail;
    String date_;
    String time_;

    public Messanger(String sender, String type, String mail, String sender_name) {
        this.sender = sender;
        this.type = type;
        this.mail = mail;
        this.sender_name = sender_name;
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        DateFormat dateFormat1 = new SimpleDateFormat("HH:mm:ss");
        Date date = new Date();
        Date date1 = new Date();
        String d,t;
        d=(String)dateFormat.format(date);
        t=(String)dateFormat1.format(date1);
        date_=d;
        time_=t;
    }

    String sender_name;

    public Messanger() {
        sender_name=sender=type=mail=date_=time_="Omi";
    }

}
