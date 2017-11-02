package com.example.aniomi.myapplication;

/**
 * Created by aniomi on 9/30/17.
 */
import android.content.Context;
import android.content.Intent;
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

public class ListCreate {
    DatabaseReference mDatabase=FirebaseDatabase.getInstance().getReference("Users");
    public List<Students> lists=new ArrayList<>();
    public ListCreate()
    {
        lists.add(new Students());
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override

            public void onDataChange(DataSnapshot dataSnapshot) {
                lists.add(new Students());
                for (DataSnapshot childSnapshot: dataSnapshot.getChildren()) {
                    lists.add(new Students());
                }}
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    public List<Students> getLists()
    {
        return lists;
    }
    public int getcnt()
    {
        return lists.size();
    }
}
