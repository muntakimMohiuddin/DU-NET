package com.example.aniomi.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class OpeningActivity extends AppCompatActivity {



    static String chatter="Omi";
    static String chattername="Omi";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opening);
        /*In_out_tab fragment=new In_out_tab();
        android.support.v4.app.FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame,fragment);
        fragmentTransaction.commit();*/

        logIn_Fragment fragment=new logIn_Fragment();
        android.support.v4.app.FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame,fragment);
        fragmentTransaction.commit();
    }

}
