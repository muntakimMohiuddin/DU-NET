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
import android.widget.RelativeLayout;
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
    Context context;

    public nearbyAdapter(Context context, int textViewResourceId, ArrayList objects) {
        super(context, textViewResourceId, objects);
        studentList = objects;
        this.context = context;
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
        RelativeLayout relativeLayout = v.findViewById(R.id.grid_view_items);
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

        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder mBuilder=new AlertDialog.Builder(context);
                final View mView=LayoutInflater.from(context).inflate(R.layout.fragment_profile_my,null);
                final ImageView imageView=(ImageView) mView.findViewById(R.id.imageView);
                MainActivity.setImageFromStorage(context,"images/"+studentList.get(position).uid+".jpg",imageView);
                TextView t1,t2,t3,t4,t5;
                t1=mView.findViewById(R.id.t1);
                t2=mView.findViewById(R.id.t2);
                t3=mView.findViewById(R.id.t3);
                t4=mView.findViewById(R.id.t4);
                t5=mView.findViewById(R.id.t5);
                t5.setVisibility(View.GONE);
                t1.setText(studentList.get(position).name);
                t2.setText(studentList.get(position).dept);
                t3.setText(studentList.get(position).year+" Year");
                t4.setText("Blood Group : "+studentList.get(position).blood);
               // t5.setText("location : "+holder.text6.getText().toString());
                mBuilder.setView(mView);
                AlertDialog alertDialog=mBuilder.create();
                alertDialog.show();
            }
        });
        return v;

    }

}
