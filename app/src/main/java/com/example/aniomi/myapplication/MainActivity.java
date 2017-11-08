package com.example.aniomi.myapplication;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    NavigationView navigationView =null;
    Toolbar toolbar=null;
    int it = 0;
    CompoundButton compoundButton;

    DatabaseReference mDatabase;
    Event_Search event_search = new Event_Search();
    static void setImageFromStorage(Context context,String uri,ImageView imageView)
    {
        StorageReference storageRef;// = FirebaseStorage.getInstance().getReference();
        StorageReference forestRef;

        storageRef = FirebaseStorage.getInstance().getReference();

        forestRef = storageRef.child(uri);
        Glide.with(context).using(new FirebaseImageLoader())
                .load(forestRef)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .transform(new CircleTransform(context))
                .into(imageView);
    }
    static void setImageFromStorageNonCircle(Context context,String uri,ImageView imageView)
    {
        StorageReference storageRef;// = FirebaseStorage.getInstance().getReference();
        StorageReference forestRef;

        storageRef = FirebaseStorage.getInstance().getReference();

        forestRef = storageRef.child(uri);
        Glide.with(context).using(new FirebaseImageLoader())
                .load(forestRef)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(imageView);
    }
    static <Type> List<Type> createListFromFirebase(Type temp,Object obj,DataSnapshot dataSnapshot) throws ClassNotFoundException {
        obj.getClass();
        List<Type> list=new ArrayList<>();

        for(DataSnapshot users : dataSnapshot.getChildren())
        {
            Class cl=obj.getClass();
            obj=users.getValue(obj.getClass());
            list.add((Type) obj);
        }
        return list;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PdfArchive.department=Students.current.getDept();
        MainFragment fragment=new MainFragment();
        android.support.v4.app.FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame,fragment);
        fragmentTransaction.commit();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        /*View nav=getLayoutInflater().inflate(R.layout.nav_header_main, null);
        ImageView imageView=nav.findViewById(R.id.imageView);
        */

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        Menu menu = navigationView.getMenu();
        navigationView.setNavigationItemSelectedListener(this);
        MenuItem menuItem = (MenuItem) navigationView.getMenu().findItem(R.id.nav_share_location);
        compoundButton = (CompoundButton) MenuItemCompat.getActionView(menuItem);
        View v=navigationView.getHeaderView(0);
        ImageView imageView=v.findViewById(R.id.imageView);
        TextView gmail=v.findViewById(R.id.textView);
        final TextView name=v.findViewById(R.id.name);
        DatabaseReference databaseUsers= FirebaseDatabase.getInstance().getReference().child("Users");
        databaseUsers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot users : dataSnapshot.getChildren())
                {
                    Students temp=users.getValue(Students.class);

                    if(temp.getMail().equals(FirebaseAuth.getInstance().getCurrentUser().getEmail()))
                    {
                        Students.current=temp;
                        name.setText(temp.getName());
                        break;
                    }

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        gmail.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());
        setImageFromStorage(getApplicationContext(),"images/"+FirebaseAuth.getInstance().getCurrentUser().getUid()+".jpg",imageView);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        final String userID = mAuth.getCurrentUser().getUid();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("ShareLocation");
        mDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String shareLocation = new String();
                String id = new String();
                id = dataSnapshot.getKey();
                shareLocation = dataSnapshot.getValue(String.class);
                if(shareLocation.equals("true") && id.equals(userID)){
                    Location location = new Location(MainActivity.this);
                    location.getLocation();
                    compoundButton.setChecked(true);
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        compoundButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(compoundButton.isChecked()){
                    mDatabase = FirebaseDatabase.getInstance().getReference().child("ShareLocation");
                    String t = "true";
                    mDatabase.child(userID).setValue(t);
                    Location location = new Location(MainActivity.this);
                    location.getLocation();
                }
                else{
                    mDatabase = FirebaseDatabase.getInstance().getReference().child("ShareLocation");
                    String t = "false";
                    mDatabase.child(userID).setValue(t);
                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
        invalidateOptionsMenu();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            Toast.makeText(getApplicationContext(), "DONE", Toast.LENGTH_SHORT).show();
        }
        else if(id == R.id.Search) {
            event_search.Search(MainActivity.this);
        }
        else if(id == R.id.creat_group)
        {
            Group_creat fragment = new Group_creat();
            android.support.v4.app.FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame,fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
            invalidateOptionsMenu();
        }
        else if(id == R.id.action_group_creat_done)
        {
            Group_details.Creat_Group(MainActivity.this);
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            BusTab fragment=new BusTab();
            android.support.v4.app.FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame,fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
            invalidateOptionsMenu();

            // Handle the camera action
        } else if (id == R.id.nav_gallery) {
            SocialTab fragment=new SocialTab();
            android.support.v4.app.FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame,fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
            invalidateOptionsMenu();

        } else if (id == R.id.nav_slideshow) {
            Intent intent = new Intent(MainActivity.this,FindDepartment.class);
            startActivity(intent);


        } else if (id == R.id.nav_manage) {
            EventTabs fragment=new EventTabs();
            it = R.id.nav_manage;
            android.support.v4.app.FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame,fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
            invalidateOptionsMenu();

        } else if (id == R.id.nav_ebook) {
            Book_list fragment=new Book_list();
            it = R.id.nav_ebook;
            android.support.v4.app.FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame,fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
            invalidateOptionsMenu();
            invalidateOptionsMenu();

        } else if (id == R.id.nav_nearby) {
            nearby fragment = new nearby();
            android.support.v4.app.FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame,fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
            invalidateOptionsMenu();
        }

        else if (id == R.id.nav_share) {
            ProfileTab fragment=new ProfileTab();
            android.support.v4.app.FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame,fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
            invalidateOptionsMenu();


        } else if (id == R.id.nav_send) {
            Intent my=new Intent(this,post.class);
            startActivity(my);

        }
        else if (id == R.id.nav_sign_out) {
            FirebaseAuth.getInstance().signOut();
            Intent my=new Intent(this,OpeningActivity.class);
            startActivity(my);

        }

        else if(id == R.id.nav_group)
        {
            GroupTab fragment = new GroupTab();
            android.support.v4.app.FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame,fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
            invalidateOptionsMenu();
        }
        else if(id == R.id.nav_notice)
        {
            MainFragment fragment = new MainFragment();
            android.support.v4.app.FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame,fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
            invalidateOptionsMenu();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
