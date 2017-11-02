package com.example.aniomi.myapplication;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.util.ArrayList;

import in.galaxyofandroid.spinerdialog.OnSpinerItemClick;
import in.galaxyofandroid.spinerdialog.SpinnerDialog;


/**
 * A simple {@link Fragment} subclass.
 */
public class SearchBus extends Fragment {
    SpinnerDialog busSpinner, tripSpinner;
    EditText busET,tripET;
    ArrayList<String> bus = new ArrayList<>();
    ArrayList<String> trip = new ArrayList<>();

    void loadData(){

        String[] busses = {"Taranga","Choitali","Moitri","Anondo","Boshonto","Falguni"};
        String[] trips = {"up","down"};

        for (int i = 0; i <trips.length ; i++) {
            trip.add(trips[i]);
        }

        for (int i = 0; i <busses.length ; i++) {
            bus.add(busses[i]);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_search_bus, container, false);
        loadData();

        busET = (EditText) view.findViewById(R.id.bus);
        tripET = (EditText) view.findViewById(R.id.trip);

        busSpinner = new SpinnerDialog(getActivity(),bus,"Select bus:");
        busSpinner.bindOnSpinerListener(new OnSpinerItemClick() {
            @Override
            public void onClick(String s, int i) {
                busET.setText(s);
            }
        });

        tripSpinner = new SpinnerDialog(getActivity(),trip,"Select trip:");
        tripSpinner.bindOnSpinerListener(new OnSpinerItemClick() {
            @Override
            public void onClick(String s, int i) {
                tripET.setText(s);
            }
        });


        busET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                busSpinner.showSpinerDialog();
            }
        });
        tripET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tripSpinner.showSpinerDialog();
            }
        });

        return view;
    }
}