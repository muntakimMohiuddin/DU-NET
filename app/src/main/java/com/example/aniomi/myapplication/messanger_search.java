package com.example.aniomi.myapplication;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
class CustomComparator implements Comparator<Students> {
    @Override
    public int compare(Students o1, Students o2) {
        return o1.getName().compareTo(o2.getName());
    }
}
public class messanger_search extends Activity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private Context context;
    private EditText et1;
    private List<Students> list=new ArrayList<>();
    String sub="";
    private DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("Users");
    void filter()
    {
        List<Students> searchList=new ArrayList<>();
        for(int i=0;i<list.size();i++)
        {
            Students temp=list.get(i);
            if(temp.name.toLowerCase().startsWith(sub.toLowerCase()) && !temp.getUid().equals(Students.current.uid))
            {
                searchList.add(temp);
            }
        }
        Collections.sort(searchList,new CustomComparator());
        adapter=new messanger_search_adapter(searchList,this);
        recyclerView.setAdapter(adapter);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messanger_search);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                list.clear();

                for(DataSnapshot users : dataSnapshot.getChildren())
                {


                    Students temp=new Students();
                    ////
                    temp=users.getValue(Students.class);
                    list.add(temp);



                }
                filter();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        et1=(EditText) findViewById(R.id.et1);
        et1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                sub=et1.getText().toString();
                filter();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        recyclerView=(RecyclerView) findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setAutoMeasureEnabled(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setNestedScrollingEnabled(false);

    }
}
