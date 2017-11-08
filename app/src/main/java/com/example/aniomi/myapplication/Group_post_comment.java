package com.example.aniomi.myapplication;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Group_post_comment extends AppCompatActivity {
    int current=1;
    static posto currentpost=new posto();
    private ImageButton b1,b2,sendb,upb,downb;
    private ImageView imageView;
    private int total;
    private TextView t1,t5,t2,t3,t4;
    private EditText et1;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private int toshow;
    private List<comment> list=new ArrayList<>();
    @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
    void filter()
    {
        List<comment> temp=new ArrayList<>();
        for(int i=0;i<toshow;i++)
        {
            temp.add(list.get(i));
        }
        adapter=new postdetailsadapter(temp,getApplicationContext());

        recyclerView.setAdapter(adapter);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_post_comment);
        total=Integer.parseInt(currentpost.cnt);
        b1=findViewById(R.id.b1);
        b2=findViewById(R.id.b2);
        sendb=findViewById(R.id.sendb);
        setTitle("POST");
        t1=findViewById(R.id.t1);
        t2=findViewById(R.id.t2);
        t3=findViewById(R.id.t3);
        t4=findViewById(R.id.t4);
        t5=findViewById(R.id.t5);
        et1=findViewById(R.id.et1);
        t1.setText(currentpost.getSen());
        t3.setVisibility(View.GONE);
        t4.setText(currentpost.getDescription());
        imageView=findViewById(R.id.imageView);
        recyclerView=findViewById(R.id.recycler);
        ImageView profile=findViewById(R.id.profile);
        MainActivity.setImageFromStorage(getApplicationContext(),"images/"+currentpost.getDept()+".jpg",profile);
        final DatabaseReference databaseUsers= FirebaseDatabase.getInstance().getReference().child("comments").child(currentpost.getId());
        databaseUsers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                synchronized (list){
                    list.clear();

                    for(DataSnapshot users : dataSnapshot.getChildren())
                    {


                        comment temp=new comment();

                        temp=users.getValue(comment.class);

                        list.add(temp);
                        adapter=new postdetailsadapter(list,getApplicationContext());

                        recyclerView.setAdapter(adapter);
                    }

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setAutoMeasureEnabled(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setNestedScrollingEnabled(false);

        recyclerView.setHasFixedSize(true);
        adapter=new postdetailsadapter(list,getApplicationContext());

        recyclerView.setAdapter(adapter);
        sendb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
                DateFormat dateFormat1 = new SimpleDateFormat("HH:mm:ss");
                Date date = new Date();
                Date date1 = new Date();
                String d,t;
                d=(String)dateFormat.format(date);
                t=(String)dateFormat1.format(date1);
                comment temp=new comment(Students.current.getUid(),Students.current.getName(),et1.getText().toString(),d,t);
                databaseUsers.push().setValue(temp);

                et1.setText("");
            }
        });

        if(total != 0){
            if(total == 1)
            {
                View B=b1;
                B.setVisibility(View.GONE);
                B=b2;
                B.setVisibility(View.GONE);
            }
            MainActivity.setImageFromStorageNonCircle(getApplicationContext(),"Group_post_image/"+currentpost.id+1+".jpg",imageView);
            b1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(current==1) current=total;
                    else current--;
                    MainActivity.setImageFromStorageNonCircle(getApplicationContext(),"Group_post_image/"+currentpost.id+(current)+".jpg",imageView);
                }
            });
            b2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(current==total) current=1;
                    else current++;
                    MainActivity.setImageFromStorageNonCircle(getApplicationContext(),"Group_post_image/"+currentpost.id+(current)+".jpg",imageView);
                }
            });
        }
        else
        {
            View B=b1;
            B.setVisibility(View.GONE);
            B=b2;
            B.setVisibility(View.GONE);
            B=imageView;
            B.setVisibility(View.GONE);
        }

    }
}

