package com.example.aniomi.myapplication;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Group_Search extends Fragment {
    private ImageButton drop,searchButton;
    private EditText text50;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private DatabaseReference mDatabase;
    private CardView cardView;
    ArrayList<Group_details> groups=new ArrayList<Group_details>();
    public Group_Search() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_group__search,container,false);
        searchButton = (ImageButton) view.findViewById(R.id.searchImage);
        drop = (ImageButton)  view.findViewById(R.id.imageButton10);
        text50 = (EditText)  view.findViewById(R.id.editText20);
        recyclerView=(RecyclerView)  view.findViewById(R.id.recycler1);
        final String userID = Students.current.getUid();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Groups");
        cardView = (CardView)  view.findViewById(R.id.cvSearch);
        final Group_search_adapter adapter=new Group_search_adapter(groups);



        text50.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                final String[] string = {text50.getText().toString()};
                mDatabase.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        groups.clear();

                        for(DataSnapshot users : dataSnapshot.getChildren())
                        {
                            Group_details temp = new Group_details();
                            String  t = null;
                            temp=users.getValue(Group_details.class);
                            t = temp.getName();
                            t = t.toLowerCase();
                            string[0] = string[0].toLowerCase();
                            if(t.indexOf(string[0]) != -1)
                                groups.add(temp);
                            else if(string[0].indexOf(t) != -1)
                                groups.add(temp);

                            recyclerView.setAdapter(adapter);
                        }
                        Group_details.staticList = groups;
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setNestedScrollingEnabled(false);

        return view;
    }
}
