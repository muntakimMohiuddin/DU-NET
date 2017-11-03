package com.example.aniomi.myapplication;

/**
 * Created by aniomi on 11/2/17.
 */

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

/**
 * Created by asif on 10/14/17.
 */

public class nearbyAdapter extends ArrayAdapter {

    ArrayList<Students> studentList=new ArrayList<>();

    public nearbyAdapter(Context context, int textViewResourceId, ArrayList objects) {
        super(context, textViewResourceId, objects);
        studentList = objects;
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View v = convertView;
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        v = inflater.inflate(R.layout.activity_gridview, null);
        ImageButton imbdb=(ImageButton) v.findViewById(R.id.imageButton);
        TextView textView = (TextView) v.findViewById(R.id.textView);
        ImageView imageView = (ImageView) v.findViewById(R.id.imageView);
        textView.setText(studentList.get(position).name);
        imbdb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder mBuilder=new AlertDialog.Builder(getContext());
                final View mView=LayoutInflater.from(getContext()).inflate(R.layout.activity_popupfind,null);

                final Students temp=studentList.get(position);

                final EditText ett=(EditText) mView.findViewById(R.id.et2);
                Button send=(Button) mView.findViewById(R.id.b9);

                send.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String mai=ett.getText().toString();
                        String name=Students.current.name;
                        String sen=Students.current.uid;
                        String rec=temp.getUid();
                        DatabaseReference databaseUsers= FirebaseDatabase.getInstance().getReference().child("streams");
                        databaseUsers.push().setValue(new Streamo(mai,sen,rec,name));


                    }
                });
                mBuilder.setView(mView);
                AlertDialog alertDialog=mBuilder.create();
                alertDialog.show();
            }
        });
        // imageView.setImageResource(birdList.get(position).getbirdImage());
        StorageReference storageRef;// = FirebaseStorage.getInstance().getReference();
        StorageReference forestRef;

        storageRef = FirebaseStorage.getInstance().getReference();
        forestRef = storageRef.child("images/"+studentList.get(position).uid+".jpg");
        Glide.with(getContext()).using(new FirebaseImageLoader())
                .load(forestRef)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(imageView);
        return v;

    }

}
