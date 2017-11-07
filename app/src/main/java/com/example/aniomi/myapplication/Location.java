package com.example.aniomi.myapplication;

import android.content.Context;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by asif on 11/8/17.
 */

public class Location implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,LocationListener {

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 588;
    private LocationListener locationListener;
    private LocationManager locationManager;
    private DatabaseReference mDatabase,mDatabase2;
    private FirebaseAuth mAuth;
    LocationRequest mLocationRequest;
    GoogleApiClient mGoogleApiClient;
    android.location.Location mLastLocation;
    double lat, lng;
    Context context;

    Location(Context context){
        this.context = context;
    }

    public void getLocation()
    {
        /*if (ActivityCompat.checkSelfPermission(context,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            Toast.makeText(getContext(), "Location Not Granted", Toast.LENGTH_SHORT).show();
        }*/
        buildGoogleApiClient();
    }

    synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(context)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    @Override
    public void onLocationChanged(android.location.Location location) {
        lat = location.getLatitude();
        lng = location.getLongitude();
        Log.e("LocationChanged",location.getSpeed()+" "+location.getAccuracy()+" "+location.getBearing()+" "+location.getProvider()+" "+location.getAltitude()
        );

        keepLocationUpdated();
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        mLocationRequest = LocationRequest.create();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        mLocationRequest.setMaxWaitTime(5000);
        mLocationRequest.setFastestInterval(2000); // Update location every second

        try {
            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                    mGoogleApiClient);
        }
        catch (SecurityException e)
        {
            Log.e("LocatonUpdate",e+" ");
        }
        if (mLastLocation != null) {
            lat = mLastLocation.getLatitude();
            lng = mLastLocation.getLongitude();

        }

        keepLocationUpdated();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        buildGoogleApiClient();
    }


    void keepLocationUpdated(){
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users");
        mAuth = FirebaseAuth.getInstance();
        final String userID = Students.current.getUid();
        mDatabase.child(userID).child("location").setValue(lat+","+lng);
       // Toast.makeText(context, "HIIIIIIIIIII", Toast.LENGTH_SHORT).show();
    }
}
