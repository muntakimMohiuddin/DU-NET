package com.example.aniomi.myapplication;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.signature.StringSignature;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;

import static com.example.aniomi.myapplication.Group_details.getContext;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileMy extends Fragment {

    public TextView t1,t2,t3,t4,t5;
    public ProfileMy() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_profile_my, container, false);
        final ImageView imageView=(ImageView) view.findViewById(R.id.imageView);

        t1=(view).findViewById(R.id.t1);
        t2=(view).findViewById(R.id.t2);
        t3=(view).findViewById(R.id.t3);
        t4=(view).findViewById(R.id.t4);
        t5=(view).findViewById(R.id.t5);
        DatabaseReference databaseUsers= FirebaseDatabase.getInstance().getReference().child("Users");
        databaseUsers.orderByChild("uid").equalTo(Students.current.uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                for(DataSnapshot users : dataSnapshot.getChildren())
                {


                    Students temp=new Students();
                    ////
                    temp=users.getValue(Students.class);
                    Students.current.allSet(temp);

                    StorageReference storageRef;// = FirebaseStorage.getInstance().getReference();
                    StorageReference forestRef;

                    storageRef = FirebaseStorage.getInstance().getReference();
                    forestRef = storageRef.child("images/"+Students.current.uid+".jpg");
                    t1.setText("User Name : " +Students.current.name);
                    t2.setText("Department : " +Students.current.dept);
                    t3.setText("Year : " +Students.current.year);
                    t4.setText("Location : " +Students.current.location);
                    t5.setText("Blood Group : " +Students.current.blood);
                    ImageView im;
                    //forestRef.getActiveUploadTasks();
                    Glide.with(getContext()).using(new FirebaseImageLoader())
                            .load(forestRef)
                            .transform(new CircleTransform(getContext()))
                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                            .into(imageView);

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }


        });
        // Create a storage reference from our app

// Get reference to the file
        return view;

    }

}
