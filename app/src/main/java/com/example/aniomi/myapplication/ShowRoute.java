package com.example.aniomi.myapplication;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ShowRoute extends AppCompatActivity implements OnMapReadyCallback{

    GoogleMap googleMapRoute;
    MapFragment mapFragment;
    DatabaseReference dbReference;

    ArrayList<LatLng> points;

    static String busName;
    static int midPoint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_route);

        mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.only_rout_map);

        if(googleMapRoute==null){
            mapFragment.getMapAsync(this);
            drawBusRoute();
        }
    }

    void drawBusRoute(){

        points = new ArrayList<>();
        dbReference = FirebaseDatabase.getInstance().getReference(busName).child("latlng");
        dbReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Toast.makeText(getApplicationContext(),"Loading Bus Points",Toast.LENGTH_SHORT).show();

                String data = "";
                int i=0;
                for (DataSnapshot tempDataSnap : dataSnapshot.getChildren()){
                    i++;
                    data =  tempDataSnap.getValue().toString() ;
                    String temp[] = data.split("-");
                    if(temp.length < 2){
                        Toast.makeText(getApplicationContext(),"NULL OR ERROR IN DATA", Toast.LENGTH_SHORT).show();
                    }else {
                        LatLng aPoint = new LatLng(Double.parseDouble(temp[0].toString()),Double.parseDouble(temp[1].toString()));
                        points.add(aPoint);
                    }
                }
                midPoint = i/2;
                drawPolyline(points);
                LatLng focusPosition = points.get(midPoint);
                googleMapRoute.moveCamera(CameraUpdateFactory.newLatLngZoom(focusPosition,14));


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    public void drawPolyline(ArrayList<LatLng> arg){
        Toast.makeText(getApplicationContext(),"Trying To Draw Bus Points",Toast.LENGTH_SHORT).show();

        PolylineOptions polylineOptions = new PolylineOptions();

        polylineOptions.addAll(arg);
        polylineOptions.width(5).color(Color.BLUE);

        googleMapRoute.addPolyline( polylineOptions );
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMapRoute = googleMap;
    }
}