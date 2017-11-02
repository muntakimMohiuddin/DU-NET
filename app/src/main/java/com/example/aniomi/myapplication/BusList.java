package com.example.aniomi.myapplication;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class BusList extends AppCompatActivity {

    private RecyclerView recyclerview;
    static boolean isUp;
    static String start,end,ud="";

    String time;

    TextView t;

    Button b;

    ArrayList<String> startA = new ArrayList<>();
    ArrayList<String> endA = new ArrayList<>();
    ArrayList<String> result = new ArrayList<>();

    DatabaseReference db,db2;

    final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh+mm+a");

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_list);
        recyclerview = (RecyclerView) findViewById(R.id.recycler_list);

        Calendar c = Calendar.getInstance();
        time = simpleDateFormat.format(c.getTime());

        setUpDown();
        completeTask();
    }

    void setUpDown(){
        if(isUp)
            ud = "up";
        else
            ud = "down";
    }

    void loadStartList(){
        db.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String dataSnapValue = "";
                for(DataSnapshot tempDataSnapshot : dataSnapshot.getChildren()){
                    dataSnapValue = tempDataSnapshot.getValue().toString();
                    String[] splitData = dataSnapValue.split("-"); // splitData[1] = time of bus

                    try {
                        Date timeNow = simpleDateFormat.parse(time);
                        Date timeBus = simpleDateFormat.parse(splitData[1]);

                        if(timeBus.after(timeNow)){
                            startA.add(dataSnapValue);
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    void loadResult(){
        db2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String dataSnapValue = "";
                for (DataSnapshot tempDataSnap : dataSnapshot.getChildren()){
                    dataSnapValue =  tempDataSnap.getValue().toString() ;
                    String[] splitData = dataSnapValue.split("-");
                    try{
                        Date busTime = simpleDateFormat.parse(splitData[1]);
                        Date currentTime = simpleDateFormat.parse(time);

                        if(busTime.after(currentTime)){
                            endA.add(dataSnapValue);
                        }
                    } catch (Exception e){
                        Toast.makeText(getApplicationContext(),"ERROR",Toast.LENGTH_SHORT);
                    }
                }

                for (int i = 0; i <startA.size() ; i++) {
                    if(endA.contains(startA.get(i)) ){
                        result.add(startA.get(i));
                    }
                }

                if(result.size() == 0){
                    result.add("No Bus Available-.+.+.");
                }

                recyclerview.setAdapter(new Adapter(getApplicationContext(),result));
                recyclerview.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void loadDatabase() {
        db = FirebaseDatabase.getInstance().getReference().child(start).child(ud);
        db2 = FirebaseDatabase.getInstance().getReference().child(end).child(ud);
    }

    void completeTask(){
        if(!ud.equals("")){
            loadDatabase();
            loadStartList();
            loadResult();
        }
    }
}
