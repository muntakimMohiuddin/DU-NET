package com.example.aniomi.myapplication;

/**
 * Created by aniomi on 9/30/17.
 */

public class Students {
    static Students current=new Students();
    String name;
    String pass;
    String mail;
    String dept;
    String year;
    String location;
    String blood;
    String uid;


    //static for Lat lng

    static double lat , lng;
    public Students()
    {
        name=pass=mail=dept=year=location=blood=uid="Omi";location="0.00,0.00";
    }
    public Students(String name, String pass, String mail, String dept, String year, String location, String blood,String uid){
        this.name = name;
        this.pass = pass;
        this.mail = mail;
        this.dept = dept;
        this.year = year;
        this.location = location;
        this.blood = blood;
        this.uid=uid;
    }
    public void allSet(Students s)
    {
        this.name = s.name;
        this.pass = s.pass;
        this.mail = s.mail;
        this.dept = s.dept;
        this.year = s.year;
        this.location = s.location;
        this.blood = s.blood;
        this.uid=s.uid;

    }
    boolean isValid()
    {
        boolean flag=true;
        flag=flag && (Search.curr.name.equals("none") || this.name.toLowerCase().contains(Search.curr.name.toLowerCase()));
        flag=flag && (Search.curr.dept.equals("none") || this.dept.equals(Search.curr.dept));
        flag=flag && (Search.curr.year.equals("none") || this.year.equals(Search.curr.year));
        flag=flag && (Search.curr.blood.equals("none") || this.blood.equals(Search.curr.blood));
        return flag;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
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

    public String getLoc() {
        return location;
    }
    public String getLocation() {
        return location;
    }

    public void setLocation(String loc) {
        this.location = loc;
    }

    public String getBlood() {
        return blood;
    }

    public void setBlood(String blood) {
        this.blood = blood;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
