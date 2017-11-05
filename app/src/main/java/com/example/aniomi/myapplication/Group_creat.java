package com.example.aniomi.myapplication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;

import static android.app.Activity.RESULT_OK;
import static com.example.aniomi.myapplication.Group_details.tfilePath;

public class Group_creat extends Fragment {

    private EditText name , about ,adminPass,userPass;
    private ImageView photo;
    private static final  int PICK_IMAGE_REQUEST=234;
    int request_Code = 1 ;
    public Group_creat() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_group_creat,container,false);

        getActivity().setTitle("CREATE GROUP");
        name = view.findViewById(R.id.group_name);
        about = view.findViewById(R.id.about);
        photo = view.findViewById(R.id.group_pic);
        adminPass = view.findViewById(R.id.adminPass);
        userPass = view.findViewById(R.id.userPass);

        DatabaseReference mDatabase;
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Groups");
        Group_details.tgroupID = mDatabase.push().getKey();

        photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showFileChooser();
            }
        });

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

    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            tfilePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), tfilePath);
                photo.setImageBitmap(bitmap);
                //uploadFile();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.group_creat_done,menu);
        return;
    }


}
