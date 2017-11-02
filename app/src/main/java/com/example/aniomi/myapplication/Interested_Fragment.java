package com.example.aniomi.myapplication;

/**
 * Created by aniomi on 10/24/17.
 */
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by asif on 10/2/17.
 */

public class Interested_Fragment  extends Fragment{
    private static final String TAG = "INTERSTED";
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<EventDetails> list;
    private List<String > list_keys;
    private DatabaseReference mDatabase;
    private CardView cardView;
    private FirebaseAuth mAuth;

    private Button button;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.interested_fragment,container,false);

        recyclerView=(RecyclerView) view.findViewById(R.id.recycler);
        mAuth = FirebaseAuth.getInstance();
        final String userID = Students.current.getUid();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Interested_Events").child(userID);
        cardView = (CardView) view.findViewById(R.id.cv);

        list=new ArrayList<EventDetails>();
        list_keys = new ArrayList<String>();
        adapter=new EventAdapter_Interested(list);
        mDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                EventDetails temp=new EventDetails();
                temp=dataSnapshot.getValue(EventDetails.class);
                String key = dataSnapshot.getKey();
                if(!list_keys.contains(key)) {
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
