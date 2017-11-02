package com.example.aniomi.myapplication;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import in.galaxyofandroid.spinerdialog.OnSpinerItemClick;
import in.galaxyofandroid.spinerdialog.SpinnerDialog;


/**
 * A simple {@link Fragment} subclass.
 */
public class Massege extends Fragment {
    HashMap<String,String> mp=new HashMap<String, String>();
    private EditText et1,et2;
    ArrayList<String> names=new ArrayList<>();
    private Button b1;ImageButton idb;
    public Massege() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_massege, container, false);
        DatabaseReference databaseUsers= FirebaseDatabase.getInstance().getReference().child("Users");
        databaseUsers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mp.clear();
                names.clear();
                for(DataSnapshot users : dataSnapshot.getChildren())
                {


                    Students temp=new Students();
                    ////
                    temp=users.getValue(Students.class);

                    mp.put(temp.getName(),temp.getUid());
                    if(temp.uid.equals(Students.current.uid))
                    {
                        Students.current.allSet(temp);
                    }
                    names.add(temp.name);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        b1=view.findViewById(R.id.b1);
        et1=view.findViewById(R.id.et1);
        et2=view.findViewById(R.id.et2);
        idb=view.findViewById(R.id.idb);
        final SpinnerDialog deptdialog=new SpinnerDialog(getActivity(),names,"Select User");
        deptdialog.bindOnSpinerListener(new OnSpinerItemClick() {
            @Override
            public void onClick(String item, int position) {
                //Toast.makeText(getActivity(), item + "  " + position+"", Toast.LENGTH_SHORT).show();
                //cs.setText(item);
                et1.setText(item);
            }
        });
        idb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deptdialog.showSpinerDialog();
            }
        });
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(et1.getText().toString()!=null && et2.getText().toString()!=null)
                {
                    if(mp.containsKey(et1.getText().toString()))
                    {
                        DatabaseReference databaseUsers= FirebaseDatabase.getInstance().getReference().child("streams");
                        databaseUsers.push().setValue(new Streamo(et2.getText().toString(),Students.current.uid,mp.get(et1.getText().toString()),Students.current.name));
                    }
                    else Toast.makeText(getActivity(),"Wrong User Name",Toast.LENGTH_SHORT).show();
                }

            }
        });
        return view;
    }

}
