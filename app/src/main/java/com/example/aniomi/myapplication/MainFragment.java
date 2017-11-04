package com.example.aniomi.myapplication;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import in.galaxyofandroid.spinerdialog.OnSpinerItemClick;
import in.galaxyofandroid.spinerdialog.SpinnerDialog;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<posto> list=new ArrayList<>();
    ArrayList<String> dept=new ArrayList<>();
    public ImageButton imdb;
    SpinnerDialog deptdialog;
    String current="Any";
    Context cn;
    public MainFragment() {
        // Required empty public constructor
        current="Any";
    }

    void filter()
    {
        List<posto> temp=new ArrayList<>();
        for(int i=0;i<list.size();i++)
        {
            if(list.get(i).dept.equals(current) || current.equals("Any"))
            {
                posto t=new posto();
                t=list.get(i);
                temp.add(t);
            }
        }
        adapter=new PostAdapter(temp,cn);

        recyclerView.setAdapter(adapter);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view= inflater.inflate(R.layout.fragment_main, container, false);
        recyclerView=view.findViewById(R.id.recycler);
        cn=view.getContext();
        dept.add("CSE");dept.add("EEE");dept.add("IT");dept.add("Physics");dept.add("Mechatronics");dept.add("Microbilogy");dept.add("Any");dept.add("Pharmacy");
        imdb=view.findViewById(R.id.imdb);
        DatabaseReference databaseUsers= FirebaseDatabase.getInstance().getReference().child("Posts");
        databaseUsers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                list.clear();
                try {
                    list=MainActivity.createListFromFirebase(new posto(),new posto(),dataSnapshot);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                /*for(DataSnapshot users : dataSnapshot.getChildren())
                {


                    ////
                    posto temp=new posto();
                    temp=users.getValue(posto.class);
                    list.add(temp);


                }
                Collections.reverse(list);
                filter();*/
                Collections.reverse(list);
                filter();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        deptdialog=new SpinnerDialog(getActivity(),dept,"Select Department");
        deptdialog.bindOnSpinerListener(new OnSpinerItemClick() {
            @Override
            public void onClick(String item, int position) {
                //Toast.makeText(getActivity(), item + "  " + position+"", Toast.LENGTH_SHORT).show();
                //cs.setText(item);
                current=item;
                filter();

            }
        });
        imdb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deptdialog.showSpinerDialog();
            }
        });
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setAutoMeasureEnabled(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setNestedScrollingEnabled(false);

        recyclerView.setHasFixedSize(true);
        adapter=new PostAdapter(list,view.getContext());

        recyclerView.setAdapter(adapter);
        return view;
    }

}
