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

    MapFragment mapFragment;
    GoogleMap mGoogleMap;
    Marker mCurrLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_people);
        mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);


        if(mGoogleMap==null){
            mapFragment.getMapAsync(this);
        }

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;

        LatLng latLngNew = new LatLng(Students.lat,Students.lng);
        mCurrLocation = mGoogleMap.addMarker(new MarkerOptions().position(latLngNew).title("Your Current Location"));
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLngNew,17));
    }

}
