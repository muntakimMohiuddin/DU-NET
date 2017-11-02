package com.example.aniomi.myapplication;

/**
 * Created by aniomi on 10/1/17.
 */

public class GlobaalFields{
    static String name;
    static String pass;
    static String mail;
    static String dept;
    static String year;
    static String location;
    static String blood;
    static String uid;
    static void allset(Students s)
    {
        name = s.name;
        pass = s.pass;
        mail = s.mail;
        dept = s.dept;
        year = s.year;
        location = s.location;
        blood = s.blood;
        uid=s.uid;
    }

}
