package com.example.aniomi.myapplication;

/**
 * Created by aniomi on 10/24/17.
 */

import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

/**
 * Created by asif on 10/3/17.
 */

public class EventDetails {
    static String  tName,tDate,tTime,tDescription,tEventID,tOpenerID,tLoc;
    public String eventName ,eventDate, eventTime,eventDescription ,openerID,eventID,eventLoc;
    private FirebaseAuth mAuth;
    public static EventDetails temporary;
    public static List<Students> staticList;
    EventDetails(String eventName, String eventDate, String eventTime, String eventDescription,String eventLoc, String eventID ,String openerID)
    {
        this.eventName = eventName;
        this.eventDate = eventDate;
        this.eventTime = eventTime;
        this.eventLoc = eventLoc;
        this.eventDescription = eventDescription;
        this.eventID= eventID;
        this.openerID = openerID;
    }

    public EventDetails() {
        eventName=eventDate=eventTime=eventDescription="Me";
        eventID=openerID=eventLoc="me";
    }

    public String getEventID() {
        return eventID;
    }

    public void setEventID(String eventID) {
        this.eventID = eventID;
    }

   /* public void temporaryEvent(EventDetails temp)
    {
        temporary.eventName = temp.eventName;
        temporary.eventDate = temp.eventDate;
        temporary.eventTime = temp.eventTime;
        temporary.eventDescription = temp.eventDescription;
        temporary.eventID = temp.eventID;
        temporary.openerID = temp.openerID;
    }*/

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public String getEventTime() {
        return eventTime;
    }

    public void setEventTime(String eventTime) {
        this.eventTime = eventTime;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }

    public String getOpernerID() {
        return openerID;
    }

    public void setOpernerID(String opernerID) {
        this.openerID = opernerID;
    }

    public String getEventLoc() {
        return eventLoc;
    }

    public void setEventLoc(String eventLoc) {
        this.eventLoc = eventLoc;
    }
}
