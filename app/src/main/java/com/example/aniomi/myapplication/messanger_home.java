package com.example.aniomi.myapplication;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class messanger_home extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RelativeLayout relativeLayout;
    public List<Messanger> list=new ArrayList<>();
    public messanger_home() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view= inflater.inflate(R.layout.fragment_messanger_home, container, false);
        relativeLayout=view.findViewById(R.id.button);
        recyclerView=view.findViewById(R.id.recycler);
        DatabaseReference databaseUsers= FirebaseDatabase.getInstance().getReference().child("Messanger").child(Students.current.getUid());;
        databaseUsers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                list.clear();

                for(DataSnapshot users : dataSnapshot.getChildren())
                {

                    users=users.child("lastmessenger");
                    Messanger temp=new Messanger();
                    ////
                    temp=users.getValue(Messanger.class);
                    list.add(temp);



                }
                adapter=new messanger_home_adapter(list,view.getContext());

                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(),messanger_search.class);
                startActivity(intent);
            }
        });
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setAutoMeasureEnabled(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setNestedScrollingEnabled(false);

        recyclerView.setHasFixedSize(true);
        adapter=new messanger_home_adapter(list,view.getContext());

        recyclerView.setAdapter(adapter);
        return view;
    }

}
