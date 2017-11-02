package com.example.aniomi.myapplication;

import android.content.Context;
import android.content.Intent;
import android.graphics.Region;
import android.net.Uri;
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
 * Activities that contain this fragment must implement the
 * {@link SignIn.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SignIn#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SignIn extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    Students s=new Students();
    public SignIn() {

        // Required empty public constructor
    }
    Button b;
    EditText e1,e2;
    boolean f=false;
    Students temp;
    DatabaseReference databaseUsers=FirebaseDatabase.getInstance().getReference().child("Users");
    List<Students> lists=new ArrayList<>();

    boolean isValid(String s1,String s2)
    {
        boolean flag = false;
        for(int i=0;i<lists.size();i++)
        {
            temp=lists.get(i);
            if(s1.equals(lists.get(i).mail) && s2.equals(lists.get(i).pass))
            {
                Students.current.allSet(lists.get(i));
                s.allSet(lists.get(i));
                f=true;
                GlobaalFields.allset(lists.get(i));
                return true;
            }
        }
        return false;
    }
    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_sign_in, container, false);



        databaseUsers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                lists.clear();
                for(DataSnapshot users : dataSnapshot.getChildren())
                {
                    Students temp=users.getValue(Students.class);

                    lists.add(temp);

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        ////
        b=(Button) view.findViewById(R.id.button);
        e1=(EditText) view.findViewById(R.id.e1);
        e2=(EditText) view.findViewById(R.id.e2);

        b.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                FirebaseAuth auth=FirebaseAuth.getInstance();

                auth.createUserWithEmailAndPassword(e1.getText().toString(),e2.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isComplete())
                        {

                            if(isValid(e1.getText().toString(),e2.getText().toString()))
                            {
                                Toast.makeText(getActivity(),""+Students.current.uid+temp.name+f,Toast.LENGTH_SHORT).show();
                                Intent my=new Intent(getActivity(),MainActivity.class);
                                startActivity(my);
                            }
                            else
                            {
                                Toast.makeText(getActivity(),"Failure1"+lists.size(),Toast.LENGTH_SHORT).show();
                            }
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
