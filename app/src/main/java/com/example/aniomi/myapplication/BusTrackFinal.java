package com.example.aniomi.myapplication;

import android.*;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;

public class BusTrackFinal extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,LocationListener,OnMapReadyCallback{
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;


    TextView t;
    Button btt;

    LocationRequest mLocationRequest;
    GoogleApiClient mGoogleApiClient;
    MapFragment mapFragment;
    Location mLastLocation;

    static Boolean isActivityActive = false;
    static Boolean readyForOut = false;
    static Boolean areYouOnBus = false ;
    static String busName;
    static String upDown ;
    static String busTime ;

    static ArrayList<LatLng> points;

    double lat, lng;
    GoogleMap mGoogleMap;
    Marker mCurrLocation;

    DatabaseReference db,routePolyDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        isActivityActive = true;
        readyForOut = false;
        areYouOnBus = false ;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_track_final);

        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        db = FirebaseDatabase.getInstance().getReference(busName).child(upDown).child("latlng").child(busTime);

        t = (TextView) findViewById(R.id.tv);
        btt = (Button) findViewById(R.id.b);
        mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);

        if(mGoogleMap==null){
            mapFragment.getMapAsync(this);
            drawBusRoute();
        }

        //drawBusRoute();

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(getApplicationContext(), "Location Not Granted", Toast.LENGTH_SHORT).show();
        }

        btt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(readyForOut==false){
                    getIn();
                }
                else {
                    getOut();
                }
            }
        });
        buildGoogleApiClient();
    }


    // For loading map
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
    }


    //
    //      FUNCTIONS FOR SENDING AND FETCHING LOCATION
    //

    void keepLocationUpdated() {
        if(isActivityActive){
            if (areYouOnBus) {
                sendYourLocation();
            } else {
                getBusLocation();
            }
        }
    }

    void sendYourLocation(){
        LatLng latLngNew = new LatLng(lat,lng);

        if(mCurrLocation != null){
            mCurrLocation.remove();
        }

        mCurrLocation = mGoogleMap.addMarker(new MarkerOptions().position(latLngNew).title("Your Current Location").icon(BitmapDescriptorFactory.fromResource(R.drawable.car)));
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLngNew,17));

        db.child("lat").setValue(lat);
        db.child("lng").setValue(lng);
    }

    void getBusLocation(){
        db.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Double latLng[] = new Double[2];
                int i = 0;
                for (DataSnapshot tempDataSnap : dataSnapshot.getChildren()){
                    latLng[i] = Double.parseDouble( tempDataSnap.getValue().toString() );
                    i++;
                }

                if(mCurrLocation != null){
                    mCurrLocation.remove();
                }

                LatLng latLngNew = new LatLng(latLng[0],latLng[1]);
                mCurrLocation = mGoogleMap.addMarker(new MarkerOptions().position(latLngNew).title("Bus Current Location").icon(BitmapDescriptorFactory.fromResource(R.drawable.car)));
                mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLngNew,17));
                t.setText("Current Location of "+busName);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    //      FUNCTIONS FOR GOOGLE MAP FUSED LOCATION API

    @Override
    public void onConnected(Bundle bundle) {
        mLocationRequest = LocationRequest.create();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        mLocationRequest.setMaxWaitTime(5000);
        mLocationRequest.setInterval(3000);

        try {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        }
        catch (SecurityException e){
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
    public void onLocationChanged(Location location) {
        lat = location.getLatitude();
        lng = location.getLongitude();
        keepLocationUpdated();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        buildGoogleApiClient();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mGoogleApiClient.disconnect();
    }

    synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    //      Drawing polygon  of  bus  route

    void drawBusRoute(){
        points = new ArrayList<>();
        routePolyDB = FirebaseDatabase.getInstance().getReference(busName).child("latlng");
        routePolyDB.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String data = "";
                for (DataSnapshot tempDataSnap : dataSnapshot.getChildren()){
                    data =  tempDataSnap.getValue().toString() ;
                    String temp[] = data.split("-");
                    if(temp.length < 2){
                        Toast.makeText(getApplicationContext(),"NULL OR ERROR IN DATA", Toast.LENGTH_SHORT).show();
                    }else {
                        LatLng aPoint = new LatLng(Double.parseDouble(temp[0].toString()),Double.parseDouble(temp[1].toString()));
                        points.add(aPoint);
                    }
                }
                drawPolyline(points);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void drawPolyline(ArrayList<LatLng> arg){
        PolylineOptions polylineOptions = new PolylineOptions();
        polylineOptions.addAll(arg);
        polylineOptions.width(5).color(Color.BLUE);
        mGoogleMap.addPolyline( polylineOptions );
    }

    void getIn(){
        areYouOnBus = true;
        readyForOut = true;
        t.setText("YOU ARE IN THE BUS !");
        btt.setText("I'M OUT");
        buildGoogleApiClient();
    }

    void getOut(){
        isActivityActive = false;
        finish();
    }
}