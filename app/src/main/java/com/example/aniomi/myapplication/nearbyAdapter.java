package com.example.aniomi.myapplication;

/**
 * Created by aniomi on 11/2/17.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.firebase.ui.storage.images.FirebaseImageLoader;
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
    public View getView(int position, View convertView, ViewGroup parent) {

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
