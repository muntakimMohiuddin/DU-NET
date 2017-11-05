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

        logIn_Fragment fragment=new logIn_Fragment();
        android.support.v4.app.FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame,fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {
        //
    }
}
