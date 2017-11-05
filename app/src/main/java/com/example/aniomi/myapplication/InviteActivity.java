package com.example.aniomi.myapplication;

/**
 * Created by aniomi on 10/24/17.
 */
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import in.galaxyofandroid.spinerdialog.OnSpinerItemClick;
import in.galaxyofandroid.spinerdialog.SpinnerDialog;

public class InviteActivity extends AppCompatActivity {
    private ImageButton drop,searchButton;
    private EditText text50;
    private ImageButton inviteAll;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private DatabaseReference mDatabase;
    private CardView cardView;
    private FirebaseAuth mAuth;
    SpinnerDialog attrdialog ,deptdialog;

    ArrayList<String> attr=new ArrayList<>();
    ArrayList<String> dept=new ArrayList<>();
    ArrayList<String> year=new ArrayList<>();
    private List<Students> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite);
        setTitle("INVITE PEOPLE");

        searchButton = (ImageButton) findViewById(R.id.searchImage);
        drop = (ImageButton) findViewById(R.id.imageButton10);
        text50 = (EditText) findViewById(R.id.editText20);
        recyclerView=(RecyclerView) findViewById(R.id.recycler1);
        final String userID = Students.current.getUid();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users");
        cardView = (CardView) findViewById(R.id.cvSearch);
        attr.add("Name");attr.add("Department");attr.add("Year");attr.add("Blood Group");attr.add("Location");
        list = new ArrayList<Students>();
        EventDetails.staticList = list;
        adapter=new InviteAdapter(this,list);
        final String[] searchField = {"Name"};

        drop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attrdialog=new SpinnerDialog(InviteActivity.this,attr,"Select Field");
                attrdialog.bindOnSpinerListener(new OnSpinerItemClick() {
                    @Override
                    public void onClick(String s, int i) {
                        searchField[0] = s;
                    }
                });
                attrdialog.showSpinerDialog();
            }
        });

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String[] string = {text50.getText().toString()};
                mDatabase.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        list.clear();
                        for(DataSnapshot users : dataSnapshot.getChildren())
                        {
                            Students temp=new Students();
                            String  t = null;
                            temp=users.getValue(Students.class);
                            if(searchField[0] == "Name")
                                t = temp.getName();
                            else if(searchField[0] == "Department")
                                t = temp.getDept();
                            else if(searchField[0] == "Year")
                                t = temp.getYear();
                            else if(searchField[0] == "Blood Group")
                                t = temp.getBlood();
                            else if(searchField[0] == "Location")
                                t = temp.getLoc();

                            t = t.toLowerCase();
                            string[0] = string[0].toLowerCase();
                            if(t.indexOf(string[0]) != -1)
                                list.add(temp);
                            else if(string[0].indexOf(t) != -1)
                                list.add(temp);

                            recyclerView.setAdapter(adapter);
                        }
                        EventDetails.staticList = list;
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


            }
        });

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
                        list.clear();

                        for(DataSnapshot users : dataSnapshot.getChildren())
                        {
                            Students temp=new Students();
                            String  t = null;
                            temp=users.getValue(Students.class);
                            if(searchField[0] == "Name")
                                t = temp.getName();
                            else if(searchField[0] == "Department")
                                t = temp.getDept();
                            else if(searchField[0] == "Year")
                                t = temp.getYear();
                            else if(searchField[0] == "Blood Group")
                                t = temp.getBlood();
                            else if(searchField[0] == "Location")
                                t = temp.getLoc();

                            t = t.toLowerCase();
                            string[0] = string[0].toLowerCase();
                            if(t.indexOf(string[0]) != -1)
                                list.add(temp);
                            else if(string[0].indexOf(t) != -1)
                                list.add(temp);

                            recyclerView.setAdapter(adapter);
                        }
                        EventDetails.staticList = list;
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
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setNestedScrollingEnabled(false);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.invite_all, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_menu_done:
                if(EventDetails.staticList.size() != 0) {
                    EventDetails temp = new EventDetails();
                    temp.eventName = EventDetails.tName;
                    temp.eventDate = EventDetails.tDate;
                    temp.eventTime = EventDetails.tTime;
                    temp.eventDescription = EventDetails.tDescription;
                    temp.eventID = EventDetails.tEventID;
                    temp.openerID = EventDetails.tOpenerID;
                    temp.eventLoc = EventDetails.tLoc;
                    List<Students> studentsList = EventDetails.staticList;
                    for (int i = 0; i < studentsList.size(); i++) {
                        Students students = studentsList.get(i);
                        mDatabase = FirebaseDatabase.getInstance().getReference().child("Invitation_Events");
                        mDatabase.child(students.getUid()).child(temp.eventID).setValue(temp);
                        Toast.makeText(InviteActivity.this, "DONE", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }
        return true;
    }
}