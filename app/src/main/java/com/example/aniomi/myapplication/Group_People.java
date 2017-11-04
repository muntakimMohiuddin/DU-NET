package com.example.aniomi.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import in.galaxyofandroid.spinerdialog.SpinnerDialog;

public class Group_People extends AppCompatActivity {

    private ImageButton drop,searchButton;
    private EditText text50;
    private ImageButton inviteAll;
    private RecyclerView recyclerView,recyclerView2;
    private RecyclerView.Adapter adapter,adapter2;
    private DatabaseReference mDatabase,mDatabase2;
    private CardView cardView;
    private FirebaseAuth mAuth;
    SpinnerDialog attrdialog ,deptdialog;

    ArrayList<String> attr=new ArrayList<>();
    ArrayList<String> dept=new ArrayList<>();
    ArrayList<String> year=new ArrayList<>();
    private ArrayList<Students> list;
    private ArrayList<Students> list2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group__people);

        searchButton = (ImageButton) findViewById(R.id.searchImage);
        drop = (ImageButton) findViewById(R.id.imageButton10);
        text50 = (EditText) findViewById(R.id.editText20);
        recyclerView=(RecyclerView) findViewById(R.id.recycler1);
        recyclerView2=(RecyclerView) findViewById(R.id.recycler2);
        final String userID = Students.current.getUid();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Group_Admin").child(Group_details.tgroupID);

        list = new ArrayList<Students>();
        list2 = new ArrayList<Students>();
        adapter=new People_admin_Adapter(this,list);
        adapter2=new People_member_Adapter(this,list2);
        final String[] searchField = {"Name"};
        list.add(new Students());
        list2.add(new Students());

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                list.clear();
                for(DataSnapshot users : dataSnapshot.getChildren())
                {
                    Students temp=new Students();
                    String  t = null;
                    temp=users.getValue(Students.class);
                    list.add(temp);
                    recyclerView.setAdapter(adapter);
                };
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setNestedScrollingEnabled(false);


        mDatabase2 = FirebaseDatabase.getInstance().getReference().child("Group_People").child(Group_details.tgroupID);
        mDatabase2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                list2.clear();
                for(DataSnapshot users : dataSnapshot.getChildren())
                {
                    Students temp=new Students();
                    String  t = null;
                    temp=users.getValue(Students.class);
                    list2.add(temp);
                    recyclerView2.setAdapter(adapter2);
                };
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        recyclerView2.setHasFixedSize(true);
        recyclerView2.setLayoutManager(new LinearLayoutManager(this));
        recyclerView2.setNestedScrollingEnabled(false);
    }
}
