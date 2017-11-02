package com.example.aniomi.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.content.Intent;
import java.text.SimpleDateFormat;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.StaticLayout;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
public class activity_bus extends AppCompatActivity {

        static routTime selection;
        EditText startE,endE,busE,udE;
        Button searchRoute,searchBus,trackBus;
        TextView infoT,timeT;
        DatabaseReference db;


        String time,selectedBus;
        static String selectedTime;
        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh+mm+a");

        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_bus);

            Calendar c = Calendar.getInstance();
            time = simpleDateFormat.format(c.getTime());

            infoT = (TextView) findViewById(R.id.bus_info);
            timeT = (TextView) findViewById(R.id.b_time);

            searchRoute = (Button) findViewById(R.id.search_rout_button);
            searchBus = (Button) findViewById(R.id.search_bus_button);
            trackBus = (Button) findViewById(R.id.track_button);

            trackBus.setVisibility(View.INVISIBLE);

            startE = (EditText) findViewById(R.id.start);
            endE = (EditText) findViewById(R.id.end);

            busE = (EditText) findViewById(R.id.bus);
            udE = (EditText) findViewById(R.id.trip);

            // FOR FINDING BUSES FOR SELECTED ROUTE
            searchRoute.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    searchForSelectedRoute();
                }
            });

            // FOR TRACKING A BUS BASED ON BUS_NAME AND TRIP
            searchBus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    searchForBus();
                }
            });

            trackBus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    trackSelectedBus();
                }
            });
        }

    void setText(){
        infoT.setText(selectedBus);
    }

    void searchForSelectedRoute(){
        String start = startE.getText().toString().trim();
        String end = endE.getText().toString().trim();

        routTime rtNew = new routTime(start,end,false);
        BusList.start = start;
        BusList.end = end;

        if(start.equals("DU Campus")){
            BusList.isUp = false;
        }
        else if(end.equals("DU Campus")){
            BusList.isUp = true;
        }

        selection = rtNew;

        Intent intent = new Intent(this, BusList.class);
        startActivity(intent);
    }

    void searchForBus(){

        String start =  busE.getText().toString().trim();
        String end = udE.getText().toString().trim();

        if(start.equals("") || end.equals("")){
            Toast.makeText(getApplicationContext(),"Enter bus name and trip",Toast.LENGTH_SHORT).show();
        }else {
            routTime rtNew = new routTime(start,end,true);
            selection = rtNew;

            db = FirebaseDatabase.getInstance().getReference(selection.getStartOrBus()).child(selection.getEndOrUpdown()).child("bus");
            db.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String tmp = "";
                    for (DataSnapshot tempDataSnap : dataSnapshot.getChildren()){
                        tmp =  tempDataSnap.getValue().toString() ;

                        try{
                            Date timeBus = simpleDateFormat.parse(tmp);
                            Date timeNow = simpleDateFormat.parse(time);

                            if(timeBus.after(timeNow)){
                                String[] tm = tmp.split("\\+");
                                selectedBus = selection.getStartOrBus() +"\n"+ tm[0]+":"+tm[1]+" "+ tm[2] ;
                                selectedTime = tm[0]+"+"+tm[1]+"+"+ tm[2];

                                setText();
                                trackBus.setVisibility(View.VISIBLE);

                                break;
                            }
                        } catch (Exception e){
                            Toast.makeText(getApplicationContext(),"ERROR",Toast.LENGTH_SHORT);
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }
    void trackSelectedBus(){
        BusTrackFinal.busName = selection.startOrBus;
        BusTrackFinal.upDown = selection.endOrUpdown;
        BusTrackFinal.busTime = selectedTime;
        Intent intent = new Intent(this, BusTrackFinal.class);
        startActivity(intent);
    }
}
