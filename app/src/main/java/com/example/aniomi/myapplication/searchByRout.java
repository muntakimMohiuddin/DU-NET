package com.example.aniomi.myapplication;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;

import in.galaxyofandroid.spinerdialog.OnSpinerItemClick;
import in.galaxyofandroid.spinerdialog.SpinnerDialog;


public class searchByRout extends Fragment {
    SpinnerDialog startSpinner, endSpinner;
    EditText startText,endText;
    String sStr;
    ArrayList<String> start = new ArrayList<>();
    ArrayList<String> end = new ArrayList<>();

    String[] locations = {"DU Campus","Mohammadpur","Shankar", "Zigatola", "Dhanmondi 15", "Dhanmondi 27"
            ,"Mugda","Shaymoli","Mirpur 10", "Moitri Bus Stop", "Shahbag", "Uttara","Khilgaon","Kazipara"};

    void loadData(){
        for(int i=0;i<locations.length;i++){
            start.add(locations[i]);
            end.add(locations[i]);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_search_by_rout, container, false);
        loadData();
        startText = (EditText) view.findViewById(R.id.start);
        endText = (EditText) view.findViewById(R.id.end);

        startSpinner = new SpinnerDialog(getActivity(),start,"Select location:");
        startSpinner.bindOnSpinerListener(new OnSpinerItemClick() {
            @Override
            public void onClick(String s, int i) {
                startText.setText(s);
            }
        });

        startText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startSpinner.showSpinerDialog();
            }
        });

        endSpinner = new SpinnerDialog(getActivity(),end,"Select location:");
        endSpinner.bindOnSpinerListener(new OnSpinerItemClick() {
            @Override
            public void onClick(String s, int i) {
                endText.setText(s);
            }
        });

        endText.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                sStr = startText.getText().toString().trim();
                end.remove(sStr);
                endSpinner.showSpinerDialog();
            }
        });

        return view;
    }
}