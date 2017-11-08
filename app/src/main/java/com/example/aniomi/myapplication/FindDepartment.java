package com.example.aniomi.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import in.galaxyofandroid.spinerdialog.OnSpinerItemClick;
import in.galaxyofandroid.spinerdialog.SpinnerDialog;

public class FindDepartment extends AppCompatActivity implements OnMapReadyCallback{

    EditText dept;
    Button search;

    SpinnerDialog deptSpinner;

    GoogleMap googleMapRoute;
    MapFragment mapFragment;

    Marker mCurrLocation;

    ArrayList<String> deptNames = new ArrayList<>();
    static String selectedDept="";

    DatabaseReference dbReference,dbLoad;

    void load(){

        dbLoad = FirebaseDatabase.getInstance().getReference("Departments");
        dbLoad.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot objectSnap : dataSnapshot.getChildren()){
                    String data = objectSnap.getKey();
                    deptNames.add(data);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_department);

        load();

        dept = (EditText) findViewById(R.id.dept_name);
        dept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deptSpinner.showSpinerDialog();
            }
        });
        search = (Button) findViewById(R.id.button_s);
        mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.du_map);

        deptSpinner = new SpinnerDialog(this,deptNames,"Select department :");
        deptSpinner.bindOnSpinerListener(new OnSpinerItemClick() {
            @Override
            public void onClick(String s, int i) {
                dept.setText(s);
                selectedDept = dept.getText().toString().trim() ;
            }
        });

        if(googleMapRoute==null){
            mapFragment.getMapAsync(this);
        }

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!selectedDept.equals(""))
                    drawMarker(selectedDept);
                else
                    Toast.makeText(getApplicationContext(),"Select a department first",Toast.LENGTH_SHORT).show();
            }
        });
    }

    void drawMarker(String name){
        if(googleMapRoute==null){
            mapFragment.getMapAsync(this);
            dropMarker(name);
        }
        dropMarker(name);
    }

    void dropMarker(String nam){
        dbReference = FirebaseDatabase.getInstance().getReference("Departments").child(nam);
        dbReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Double latLng[] = new Double[2];
                int i = 0;
                for (DataSnapshot tempDataSnap : dataSnapshot.getChildren()){
                    latLng[i] = Double.parseDouble( tempDataSnap.getValue().toString() );
                    i++;
                }

                LatLng markerPoint = new LatLng(latLng[0],latLng[1]);

                if(mCurrLocation != null){
                    mCurrLocation.remove();
                }

                mCurrLocation = googleMapRoute.addMarker(new MarkerOptions().position(markerPoint).title(selectedDept));
                googleMapRoute.moveCamera(CameraUpdateFactory.newLatLngZoom(markerPoint,18));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMapRoute = googleMap;
        //googleMapRoute.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        googleMapRoute.setBuildingsEnabled(false);
    }
}