package com.example.aniomi.myapplication;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import in.galaxyofandroid.spinerdialog.OnSpinerItemClick;
import in.galaxyofandroid.spinerdialog.SpinnerDialog;
import android.support.v4.app.FragmentTransaction;

/**
 * A simple {@link Fragment} subclass.
 */
public class Find extends Fragment {

    Button ba,bc,fb,sb;
    Context mc;
    TextView ca,cs;
    EditText et1;
    SpinnerDialog attrdialog,deptdialog,yeardialog,bloodialog,tutdialog;
    RelativeLayout r1;
    int ch=-1;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<Students> list=new ArrayList<>();
    ArrayList<String> attr=new ArrayList<>();
    ArrayList<String> dept=new ArrayList<>();
    ArrayList<String> year=new ArrayList<>();
    ArrayList<String> blood=new ArrayList<>();
    ArrayList<String> tut=new ArrayList<>();
    public Find() {
        // Required empty public constructor
    }
    void init()
    {
        /*attr.add("Name");*/attr.add("Dept");attr.add("Year");attr.add("Blood Group");attr.add("Tution Needed");
        year.add("1st");year.add("2nd");year.add("3rd");year.add("4th");year.add("Masters");year.add("none");
        dept.add("CSE");dept.add("EEE");dept.add("IT");dept.add("Physics");dept.add("Mechatronics");dept.add("Microbilogy");dept.add("none");dept.add("Pharmacy");
        tut.add("Yes");tut.add("No");tut.add("none");
        blood.add("A+");blood.add("A-");blood.add("B+");blood.add("b-");blood.add("AB+");blood.add("O+");blood.add("O-");blood.add("AB-");blood.add("none");
    }
    void filter()
    {
        synchronized (list){
            List<Students> mylist=new ArrayList<>();
            for(int i=0;i<list.size();i++)
            {
                if(list.get(i).isValid())
                {
                    mylist.add(list.get(i));
                }
            }
            adapter=new FindAdapter(mylist,mc);

            recyclerView.setAdapter(adapter);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view= inflater.inflate(R.layout.fragment_find, container, false);
        ba=(Button) view.findViewById(R.id.ba);
        et1=(EditText) view.findViewById(R.id.et1);
        bc=(Button) view.findViewById(R.id.bc);
        mc=view.getContext();
        init();
        recyclerView=view.findViewById(R.id.recycler);


        DatabaseReference databaseUsers= FirebaseDatabase.getInstance().getReference().child("Users");
        databaseUsers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                synchronized (list){
                list.clear();

                for(DataSnapshot users : dataSnapshot.getChildren())
                {


                    Students temp=new Students();

                    temp=users.getValue(Students.class);

                    list.add(temp);
                    if(temp.uid.equals(Students.current.uid))
                    {
                        Students.current.allSet(temp);
                    }

                }
                filter();}
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setAutoMeasureEnabled(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setNestedScrollingEnabled(false);

        recyclerView.setHasFixedSize(true);
        adapter=new FindAdapter(list,view.getContext());

        recyclerView.setAdapter(adapter);

        attrdialog=new SpinnerDialog(getActivity(),attr,"Select Attribute");
        attrdialog.bindOnSpinerListener(new OnSpinerItemClick() {
            @Override
            public void onClick(String item, int position) {
                ch=position;

                if(ch==0) {deptdialog.showSpinerDialog();}
                if(ch==1) {yeardialog.showSpinerDialog();}
                if(ch==2) {bloodialog.showSpinerDialog();}
                if(ch==3) {tutdialog.showSpinerDialog();}
            }
        });
        yeardialog=new SpinnerDialog(getActivity(),year,"Select Year");
        yeardialog.bindOnSpinerListener(new OnSpinerItemClick() {
            @Override
            public void onClick(String item, int position) {
                Search.curr.setYear(item);
                filter();
            }
        });
        deptdialog=new SpinnerDialog(getActivity(),dept,"Select Department");
        deptdialog.bindOnSpinerListener(new OnSpinerItemClick() {
            @Override
            public void onClick(String item, int position) {
                Search.curr.setDept(item);
                filter();
            }
        });

        bloodialog=new SpinnerDialog(getActivity(),blood,"Select Bloodgroup");
        bloodialog.bindOnSpinerListener(new OnSpinerItemClick() {
            @Override
            public void onClick(String item, int position) {

                Search.curr.setBlood(item);
                filter();
            }
        });
        tutdialog=new SpinnerDialog(getActivity(),tut,"Select Tution Status");
        tutdialog.bindOnSpinerListener(new OnSpinerItemClick() {
            @Override
            public void onClick(String item, int position) {

                Search.curr.setBlood(item);
                filter();
            }
        });
        ba.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attrdialog.showSpinerDialog();//filter();

            }
        });
        bc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Search.curr.allset();
                et1.setText(null);
                filter();
            }
        });
        et1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(et1.getText().toString()==null || et1.getText().toString().equals("")) Search.curr.setName("none");
                else Search.curr.setName(et1.getText().toString());
                filter();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });



        return view;
    }

}
