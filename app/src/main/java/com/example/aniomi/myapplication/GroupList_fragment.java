package com.example.aniomi.myapplication;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class GroupList_fragment extends Fragment {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private DatabaseReference mDatabase;
    ArrayList<Group_details> groups=new ArrayList<Group_details>();
    public GroupList_fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_group_list_fragment,container,false);

        recyclerView=(RecyclerView)  view.findViewById(R.id.recycler1);
        final String userID = Students.current.getUid();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("MyGroup").child(userID);
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                groups.clear();
                for(DataSnapshot users : dataSnapshot.getChildren())
                {
                    Group_details temp = new Group_details();
                    String  t = null;
                    temp=users.getValue(Group_details.class);
                    groups.add(temp);
                }
                Collections.reverse(groups);
                adapter = new Group_list_adapter(groups);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setNestedScrollingEnabled(false);
        return view;

    }
}
