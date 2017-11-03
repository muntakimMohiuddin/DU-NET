package com.example.aniomi.myapplication;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

public class Group_creat extends Fragment {

    private EditText name , about;
    private ImageView photo;
    public Group_creat() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.attending_fragment,container,false);

        name = view.findViewById(R.id.group_name);
        about = view.findViewById(R.id.about);
        photo = view.findViewById(R.id.group_pic);



        return view;
    }
}
