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

import com.google.firebase.database.DatabaseReference;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class BusTabFind extends Fragment {

    static routTime selection;
    EditText startE,endE;
    Button searchRoute;
    DatabaseReference db;

    String time,selectedBus;
    static String selectedTime;
    final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh+mm+a");

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_bus_tab_find, container, false);

        Calendar c = Calendar.getInstance();
        time = simpleDateFormat.format(c.getTime());

        startE = (EditText) rootView.findViewById(R.id.start);
        endE = (EditText) rootView.findViewById(R.id.end);

        searchRoute = (Button) rootView.findViewById(R.id.find_bus_button);
        searchRoute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchForSelectedRoute();
            }
        });

        return rootView;
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

        Intent intent = new Intent(getContext(), BusList.class);
        startActivity(intent);
    }
}