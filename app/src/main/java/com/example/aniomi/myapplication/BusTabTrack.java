package com.example.aniomi.myapplication;

/**
 * Created by shadman on 11/4/17.
 */


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class BusTabTrack extends Fragment {

    static routTime selection;
    EditText busE,udE;
    Button searchBus,trackBus;
    TextView infoT,timeT;
    DatabaseReference db;

    String time,selectedBus;
    static String selectedTime;
    final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh+mm+a");

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_bus_tab_track, container, false);

        Calendar c = Calendar.getInstance();
        time = simpleDateFormat.format(c.getTime());

        infoT = (TextView) rootView.findViewById(R.id.bus_info);
        timeT = (TextView) rootView.findViewById(R.id.b_time);
        busE = (EditText) rootView.findViewById(R.id.bus);
        udE = (EditText) rootView.findViewById(R.id.trip);

        searchBus = (Button) rootView.findViewById(R.id.search_bus_button);
        trackBus = (Button) rootView.findViewById(R.id.track_button);

        trackBus.setVisibility(View.INVISIBLE);

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

        return rootView;
    }

    void searchForBus(){

        String start =  busE.getText().toString().trim();
        String end = udE.getText().toString().trim();

        if(start.equals("") || end.equals("")){
            Toast.makeText(getContext(),"Enter bus name and trip",Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(getContext(),"ERROR",Toast.LENGTH_SHORT);
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
        Intent intent = new Intent(getContext(), BusTrackFinal.class);
        startActivity(intent);
    }

    void setText(){
        infoT.setText(selectedBus);
    }
}