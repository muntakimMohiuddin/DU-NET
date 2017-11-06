package com.example.aniomi.myapplication;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static com.example.aniomi.myapplication.R.id.t1;


/**
 * A simple {@link Fragment} subclass.
 */
public class Stream extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<Streamo> list=new ArrayList<>();


    public Stream() {
        // Required empty public constructor
    }

    @Override
    public void onStart() {
        super.onStart();


    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view=inflater.inflate(R.layout.fragment_stream, container, false);
        recyclerView=view.findViewById(R.id.recycler);


        DatabaseReference databaseUsers= FirebaseDatabase.getInstance().getReference().child("streams");
        databaseUsers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                list.clear();

                for(DataSnapshot users : dataSnapshot.getChildren())
                {


                    Streamo temp=new Streamo();
                    ////
                    temp=users.getValue(Streamo.class);
                    if(temp.rec.equals(Students.current.uid))
                    {
                        list.add(temp);
                    }


                }
                Collections.reverse(list);
                adapter=new StreamAdapter2(list,view.getContext());

                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        /*for(int i=0;i<10;i++)
        {
            list.add(new Streamo("Message","Omi","oni","1244"));
        }*/

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setAutoMeasureEnabled(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setNestedScrollingEnabled(false);

        recyclerView.setHasFixedSize(true);
        adapter=new StreamAdapter2(list,view.getContext());

        recyclerView.setAdapter(adapter);

        return view;
    }

}
