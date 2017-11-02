package com.example.aniomi.myapplication;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import android.support.v4.app.FragmentManager;

public class popupfind extends Activity {

    static String send="Omi";
    Button b1;
    EditText e1,e2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popupfind);
        DisplayMetrics dm=new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        LinearLayout lay;
        lay=(LinearLayout) findViewById(R.id.lay);
        int width=dm.widthPixels;
        int height=dm.heightPixels;
        getWindow().setLayout(((int)(width*.85)),((int)(height*.7)));
        b1=(Button) findViewById(R.id.b9);
        e1=(EditText) findViewById(R.id.et1);
        e2=(EditText) findViewById(R.id.et2);
        e1.setText(send);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(e1.getText().toString()!=null)
                {
                    String mai=e2.getText().toString();
                    String sen=Students.current.uid;
                    String name=Students.current.name;
                    String rec=e1.getText().toString();
                    DatabaseReference databaseUsers= FirebaseDatabase.getInstance().getReference().child("streams");
                    databaseUsers.push().setValue(new Streamo(mai,sen,rec,name));
                }
            }
        });
        //getWindow().setLayout(200,200);
    }
}
