package com.example.aniomi.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DatabaseReference;

public class FindPeople extends AppCompatActivity implements OnMapReadyCallback {

    GoogleMap googleMapRoute;
    MapFragment mapFragment;
    DatabaseReference dbReference;
    GoogleMap mGoogleMap;
    Marker mCurrLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_people);
        mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);


        if(googleMapRoute==null){
            mapFragment.getMapAsync(this);
            LatLng latLngNew = new LatLng(/*Students.lat,Students.lng*/90.00,23.00);
            mCurrLocation = googleMapRoute.addMarker(new MarkerOptions().position(latLngNew).title("Your Current Location"));
            googleMapRoute.moveCamera(CameraUpdateFactory.newLatLngZoom(latLngNew,17));
        }

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMapRoute = googleMap;
    }

}
