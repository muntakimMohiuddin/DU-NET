package com.example.aniomi.myapplication;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class SignOut extends Fragment {

    private EditText p1,p2,p3,p4,p5,p6,p7;
    DatabaseReference databaseUsers=FirebaseDatabase.getInstance().getReference().child("Users");
    List<Students> lists=new ArrayList<>();
    List<String> mp=new ArrayList<>();
    public SignOut() {
        // Required empty public constructor

    }
    boolean isValid(String s1)
    {
        boolean flag = false;
        for(int i=0;i<lists.size();i++)
        {
            if(s1.equals(lists.get(i).mail))
            {
                return true;
            }
        }
        return false;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_sign_out, container, false);
        databaseUsers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                lists.clear();
                mp.clear();
                for(DataSnapshot users : dataSnapshot.getChildren())
                {
                    Students temp=users.getValue(Students.class);

                    lists.add(temp);
                    //mp.add(users.getKey());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        Button button=view.findViewById(R.id.button);
        p1=(EditText) view.findViewById(R.id.name);
        p2=(EditText) view.findViewById(R.id.pass);
        p3=(EditText) view.findViewById(R.id.mail);
        p4=(EditText) view.findViewById(R.id.dept);
        p5=(EditText) view.findViewById(R.id.year);
        p6=(EditText) view.findViewById(R.id.Location);
        p7=(EditText) view.findViewById(R.id.blood);

        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // do something
                /*Intent my=new Intent(v.getContext(),OpeningActivity.class);
                startActivity(my);*/
                FirebaseAuth auth=FirebaseAuth.getInstance();

                auth.createUserWithEmailAndPassword(p3.getText().toString(),p2.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isComplete())
                        {
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            Toast.makeText(getActivity(),"Success"/*+user.getUid()*/,Toast.LENGTH_SHORT).show();
                            String id = user.getUid();
                            Students temp=new Students(p1.getText().toString(),p2.getText().toString(),p3.getText().toString(),p4.getText().toString(),p5.getText().toString(),
                                    p6.getText().toString(),p7.getText().toString(),id);
                            if(!isValid(p3.getText().toString()))
                            {
                                databaseUsers.child(id).setValue(temp);

                                //String f=setr(p3.getText().toString());
                                //Toast.makeText(getActivity(),f,Toast.LENGTH_SHORT).show();

                            }
                            /*myref.child(id).child("name").setValue(p1.getText().toString());
                            myref.child(id).child("pass").setValue(p2.getText().toString());
                            myref.child(id).child("mail").setValue(p3.getText().toString());
                            myref.child(id).child("dept").setValue(p4.getText().toString());
                            myref.child(id).child("year").setValue(p5.getText().toString());
                            myref.child(id).child("location").setValue(p6.getText().toString());
                            myref.child(id).child("blood").setValue(p7.getText().toString());
                            myref.child(id).child("uid").setValue(id);
                            ListCreate listCreate=new ListCreate();*/


                            /*myref.setValue("LOL");
                            myref.child(id).child("name").setValue(p1.getText().toString());
                            myref.child(id).child("roll").setValue(p2.getText().toString());
                            myref.child(id).child("address").setValue(p3.getText().toString());*/

                        }
                        else
                        {
                            Toast.makeText(getActivity(),"Failure",Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });

        return view;
    }


}
