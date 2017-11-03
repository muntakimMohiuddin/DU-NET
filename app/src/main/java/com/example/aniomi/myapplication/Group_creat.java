package com.example.aniomi.myapplication;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Group_creat extends Fragment {

    private EditText name , about ,adminPass,userPass;
    private ImageView photo;
    public Group_creat() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_group_creat,container,false);

        name = view.findViewById(R.id.group_name);
        about = view.findViewById(R.id.about);
        photo = view.findViewById(R.id.group_pic);
        adminPass = view.findViewById(R.id.adminPass);
        userPass = view.findViewById(R.id.userPass);

        DatabaseReference mDatabase;
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Groups");
        Group_details.tgroupID = mDatabase.push().getKey();

        name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Group_details.tname = name.getText().toString();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        about.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Group_details.tabout = about.getText().toString();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        adminPass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Group_details.tadminPass = adminPass.getText().toString();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        userPass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Group_details.tuserPass = userPass.getText().toString();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        return view;
    }
}
