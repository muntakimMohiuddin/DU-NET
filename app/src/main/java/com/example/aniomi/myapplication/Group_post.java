package com.example.aniomi.myapplication;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import in.galaxyofandroid.spinerdialog.SpinnerDialog;

import static com.example.aniomi.myapplication.Group_details.tgroupID;

public class Group_post extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<posto> list=new ArrayList<>();
    ArrayList<String> dept=new ArrayList<>();
    public ImageButton imdb;
    SpinnerDialog deptdialog;
    String current="Any";
    Context cn;
    private ImageView photo;
    private TextView name , post , about , people;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_post);

        recyclerView=findViewById(R.id.recycler);
        photo = findViewById(R.id.imageView3);
        name = findViewById(R.id.name);
        post = findViewById(R.id.post);
        about = findViewById(R.id.about);
        people = findViewById(R.id.People);

        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Group_post.this , Group_post_creat.class);
                startActivity(intent);
            }
        });

        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(Group_post.this);
                builder1.setMessage(Group_details.tabout);
                builder1.setCancelable(true);
                builder1.setPositiveButton(
                        "Ok",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert11 = builder1.create();
                alert11.show();
            }
        });

        people.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Group_post.this,Group_People.class);
                startActivity(intent);
            }
        });
        StorageReference storageRef = FirebaseStorage.getInstance().getReference();
        StorageReference forestRef = storageRef.child("GROUPimages/"+Group_details.tgroupID+".jpg");
        Glide.with(this).using(new FirebaseImageLoader())
                .load(forestRef)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(photo);

        photo.setLayerType(View.LAYER_TYPE_HARDWARE,null);
        name.setText(Group_details.tname);

        DatabaseReference databaseUsers= FirebaseDatabase.getInstance().getReference().child("Group_post").child(tgroupID);
        databaseUsers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                list.clear();
                for(DataSnapshot users : dataSnapshot.getChildren())
                {
                    posto temp=new posto();
                    temp=users.getValue(posto.class);
                    list.add(temp);
                }
                Collections.reverse(list);
                adapter=new Group_post_adapter(list,Group_post.this);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.myFAB);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Group_post.this , Group_post_creat.class);
                startActivity(intent);
            }
        });*/

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setNestedScrollingEnabled(false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(Students.current.getUid().equals(Group_details.toperner))
            getMenuInflater().inflate(R.menu.creat_post, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_post_creat) {
            Group_details.edit = true;
            Intent intent = new Intent(Group_post.this , Group_post_creat.class);
            startActivity(intent);
        }
        return true;
    }

}
