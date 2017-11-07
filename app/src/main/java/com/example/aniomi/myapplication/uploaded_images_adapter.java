package com.example.aniomi.myapplication;

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

import java.sql.Array;
import java.util.ArrayList;

import static com.example.aniomi.myapplication.Group_details.getContext;

/**
 * Created by aniomi on 11/7/17.
 */

public class uploaded_images_adapter extends ArrayAdapter{
    ArrayList<String> studentList=new ArrayList<>();

    public uploaded_images_adapter(Context context, int textViewResourceId, ArrayList<String> objects) {
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

        v = inflater.inflate(R.layout.uploaded_images, null);
        ImageView imageView = (ImageView) v.findViewById(R.id.imageView);
        MainActivity.setImageFromStorageNonCircle(getContext(),"POSTimages/"+studentList.get(position)+".jpg",imageView);
        // imageView.setImageResource(birdList.get(position).getbirdImage());
        /*StorageReference storageRef;// = FirebaseStorage.getInstance().getReference();
        StorageReference forestRef;

        storageRef = FirebaseStorage.getInstance().getReference();
        forestRef = storageRef.child("images/"+studentList.get(position)+".jpg");
        Glide.with(getContext()).using(new FirebaseImageLoader())
                .load(forestRef)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(imageView);
        return v;*/
        return v;
    }
}
