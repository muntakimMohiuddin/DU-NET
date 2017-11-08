package com.example.aniomi.myapplication;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

/**
 * Created by asif on 11/8/17.
 */

public class LocationHashmap {
    HashMap<String ,String > hashMap = new HashMap<String, String>();

    public HashMap<String ,String > hash()
    {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("ShareLocation").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String shareLocation = new String();
                String id = new String();
                id = dataSnapshot.getKey();
                shareLocation = dataSnapshot.getValue(String.class);
                hashMap.put(id,shareLocation);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return hashMap;
    }
}
