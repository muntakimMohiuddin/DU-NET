package com.example.aniomi.myapplication;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;

import in.galaxyofandroid.spinerdialog.SpinnerDialog;


/**
 * A simple {@link Fragment} subclass.
 */
public class EventTabs extends Fragment {


    public  static TabLayout tabLayout;
    private FloatingActionButton fav;
    public  static ViewPager viewPager;
    public  static int int_items= 4;

    private FirebaseAuth mAuth;
    SpinnerDialog attrdialog;
    private Toolbar toolbar;
    private DatabaseReference mDatabase,status;

    List<EventDetails> listEvent = new ArrayList<EventDetails>();
    ArrayList<String> listName=new ArrayList<>();
    ArrayList<String> attending=new ArrayList<>();
    ArrayList<String> not_interested=new ArrayList<>();
    ArrayList<String> interested=new ArrayList<>();

    public EventTabs() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v=inflater.inflate(R.layout.fragment_event_tabs, container, false);
        tabLayout=(TabLayout)v.findViewById(R.id.tabs);
        viewPager=(ViewPager)v.findViewById(R.id.container);
        fav = (FloatingActionButton) v.findViewById(R.id.myFAB);

        viewPager.setAdapter(new EventTabsAdapter( getChildFragmentManager()));

        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                tabLayout.setupWithViewPager(viewPager);
            }
        });

        fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventDetails.tName = null;
                EventDetails.tDate = null;
                EventDetails.tTime = null;
                EventDetails.tDescription = null;
                EventDetails.tEventID = null;
                EventDetails.tOpenerID = null;
                EventDetails.tLoc = null;
                Intent intent = new Intent(getActivity(),CreatEventsActivity.class);
                intent.putExtra("tag",true);
                startActivity(intent);
            }
        });
        return v;
    }
}
