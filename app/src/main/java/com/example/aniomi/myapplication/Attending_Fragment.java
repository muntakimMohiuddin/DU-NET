package com.example.aniomi.myapplication;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

;


/**
 * Created by asif on 10/2/17.
 * ////
 */

public class Attending_Fragment extends Fragment {
    private static final String TAG = "ATTENDING";

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<EventDetails> list;
    private List<String > list_keys;
    private DatabaseReference mDatabase , mDataCount;
    private CardView cardView;
    private FirebaseAuth mAuth;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.attending_fragment,container,false);

        recyclerView=(RecyclerView) view.findViewById(R.id.recycler);
        mAuth = FirebaseAuth.getInstance();
        final String userID = Students.current.getUid();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Attending_Events").child(userID);
        mDataCount = FirebaseDatabase.getInstance().getReference().child("countEvents");
        cardView = (CardView) view.findViewById(R.id.cv);

        list=new ArrayList<EventDetails>();
        list_keys = new ArrayList<String>();
        list_keys.add("01");
        adapter=new EventAdapter(list);

        Calendar c = Calendar.getInstance();
        final int mYear = c.get(Calendar.YEAR); // current year
        final int mMonth = c.get(Calendar.MONTH); // current month
        final int mDay = c.get(Calendar.DAY_OF_MONTH); 

        mDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                EventDetails temp=new EventDetails();
                temp=dataSnapshot.getValue(EventDetails.class);
                String mDate = temp.getEventDate();
                boolean outdated = false;
                try {
                    Date date1, date2;
                    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                    date1 = formatter.parse(mDate);
                    String st2 = mDay + "/" + mMonth + "/" + mYear;
                    date2 = formatter.parse(st2);

                    if (date1.compareTo(date2) < 0) {
                        outdated = true;
                    }
                }
                catch (ParseException e)
                {
                    //
                }
                String key = dataSnapshot.getKey();
                if(!list_keys.contains(key) && outdated != true) {
                    list.add(temp);
                    list_keys.add(key);
                }

                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setAutoMeasureEnabled(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setNestedScrollingEnabled(false);

        return view;
    }

}