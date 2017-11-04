package com.example.aniomi.myapplication;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class chatbox extends Activity {


    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RelativeLayout relativeLayout;
    private Context context;
    private ImageButton b1;
    private EditText t1;private TextView t2;
    public List<Messanger> list=new ArrayList<>();
    public Date convertStringToDate(String dateString)
    {
        Date formatteddate = null;
        try{
            formatteddate = new SimpleDateFormat("yyyy/MM/dd").parse(dateString);
        }
        catch ( Exception ex ){
            System.out.println(ex);
        }
        return formatteddate;
    }
    public Date convertStringToTime(String dateString)
    {
        Date formatteddate = null;
        try{
            formatteddate = new SimpleDateFormat("HH:mm:ss").parse(dateString);
        }
        catch ( Exception ex ){
            System.out.println(ex);
        }
        return formatteddate;
    }

    public Comparator<Messanger> slopeOrder() {
        return new Comparator<Messanger>() {
            @Override
            public int compare(Messanger a, Messanger b) {
                // code here
                if(convertStringToDate(a.date_).before(convertStringToDate(b.date_))) return 0;
                else if(convertStringToDate(a.date_).after(convertStringToDate(b.date_))) return 1;
                else if(convertStringToTime(a.date_).after(convertStringToTime(b.date_))) return 1;
                return 0;
            }
        };
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatbox);
        context=this;
        DatabaseReference databaseUsers= FirebaseDatabase.getInstance().getReference().child("Messanger").child(Students.current.getUid()).child(OpeningActivity.chatter);;
        databaseUsers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                list.clear();

                for(DataSnapshot users : dataSnapshot.getChildren())
                {

                    Messanger temp=new Messanger();
                    ////
                    if(!users.getKey().equals("lastmessenger"))
                    {
                        temp=users.getValue(Messanger.class);
                        list.add(temp);

                    }



                }
                //Collections.sort(list,(a2, b2)->convertStringToDate(a2.date_).before(convertStringToDate(b2.date_))? true :( (!convertStringToDate(a2.date_).before(convertStringToDate(b2.date_)) && !convertStringToDate(a2.date_).after(convertStringToDate(b2.date_)))) ? 0 : 1);
                //Collections.sort(list,slopeOrder());
                adapter=new chatbox_adapter(list,context);

                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        t1=(EditText)findViewById(R.id.et1);
        b1=(ImageButton) findViewById(R.id.b1);
        t2=(TextView) findViewById(R.id.t2);
        t2.setText(OpeningActivity.chattername);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference databaseMessanger;
                databaseMessanger=FirebaseDatabase.getInstance().getReference().child("Messanger").child(OpeningActivity.chatter).child(Students.current.getUid());
                Messanger temp=new Messanger(Students.current.getUid(),"Send",t1.getText().toString(),Students.current.getName());

                databaseMessanger.push().setValue(temp);
                temp=new Messanger(Students.current.getUid(),"Send",t1.getText().toString(),Students.current.getName());
                databaseMessanger.child("lastmessenger").setValue(temp);
                databaseMessanger=FirebaseDatabase.getInstance().getReference().child("Messanger").child(Students.current.getUid()).child(OpeningActivity.chatter);
                temp=new Messanger(Students.current.getUid(),"Receive",t1.getText().toString(),Students.current.getName());

                databaseMessanger.push().setValue(temp);
                temp=new Messanger(OpeningActivity.chatter,"Send",t1.getText().toString(),OpeningActivity.chattername);

                databaseMessanger.child("lastmessenger").setValue(temp);
                t1.setText("");
            }
        });
        recyclerView=(RecyclerView) findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setAutoMeasureEnabled(true);
        recyclerView.setLayoutManager(layoutManager);

        adapter=new chatbox_adapter(list,this);
        recyclerView.setAdapter(adapter);
    }

}
