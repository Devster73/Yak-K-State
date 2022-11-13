package com.example.yaker;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import android.widget.ImageButton;

import android.widget.Toast;


import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.TileOverlay;
import com.google.android.gms.maps.model.TileOverlayOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;
import java.util.List;

public class YakMap extends FragmentActivity implements OnMapReadyCallback {

    private static final int PERMISSIONS_FINE_LOCATION = 22;
    GoogleMap map;
    List<Location> savedLocations;

    // Google's API for location services
    FusedLocationProviderClient fusedLocationProviderClient;

    // Config file for all settings related to FusedLocationProviderClient
    LocationRequest locationRequest;

    LocationCallback locationCallBack;
    private LocationManager locationManager;
    public GoogleMap getMap(){
        return map;
    }
    public void  gotoChat() {
        Intent a = new Intent(this, ChatList.class);
        startActivity(a);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yak_map);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        ImageButton btn = (ImageButton) findViewById(R.id.button2);


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoChat();
            }
        });

        ArrayList<Marker> markers = new ArrayList<Marker>();
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        if (ContextCompat.checkSelfPermission(YakMap.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(YakMap.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(YakMap.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10, 1, new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {

                final LatLng Hat = new LatLng(location.getLatitude(), location.getLongitude());
                for (Marker marker : markers){
                    marker.remove();
                }

                markers.clear();
                Marker me = map.addMarker(
                        new MarkerOptions()
                                .position(Hat)
                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA)));
                markers.add(me);
               // map.addMarker(new MarkerOptions().position(Hat).title("Manhattan"));

            }


        });


    /*


        locationRequest = new LocationRequest();
        locationRequest.setInterval(30000);
        locationRequest.setFastestInterval(5000);
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

        // event that is triggered whenever the update interval is met
        locationCallBack = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);

                //save the location
                updateUIValues(locationResult.getLastLocation());
                Log.d("tag2", "location: " + locationResult.getLastLocation());
            }
        };
        updateGPS();*/

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        // Customise the styling of the base map using a JSON object defined
        // in a raw resource file.
        boolean success = googleMap.setMapStyle(
                MapStyleOptions.loadRawResourceStyle(
                        this, R.raw.style_json));
        map = googleMap;



        map.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(39.1836, -96.5717)));
        map.setMinZoomPreference(12.0F);
        map.setLatLngBoundsForCameraTarget(new LatLngBounds(new LatLng(39.11678, -96.75304), new LatLng(39.28516,-96.41371)));
       LatLng l = new LatLng(39.20202070755281, -96.5938552024389);
        map.addTileOverlay(new TileOverlayOptions().tileProvider(HeatMapLayer.HeatLayer( l,80000,200)));
        map.addTileOverlay(new TileOverlayOptions().tileProvider(HeatMapLayer.HeatLayer(new LatLng(39.185692323074456, -96.57441107360347),10000,500)));
        map.addTileOverlay(new TileOverlayOptions().tileProvider(HeatMapLayer.HeatLayer(new LatLng(39.18941269075507, -96.57725816626206),100000,50)));
       // LatLng Hat = new LatLng(39.197018934687634, -96.57965725552246);
       // map.addMarker(new MarkerOptions().position(Hat).title("Manhattan"));
       // map.moveCamera(CameraUpdateFactory.newLatLng(Hat));
    }
/*
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch(requestCode) {
            case PERMISSIONS_FINE_LOCATION:
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                updateGPS();
            }
            else {
                Toast.makeText(this, "This app requires permission in order to work properly", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void updateGPS() {
        // get permissions from the user to track GPS
        // get the current location from the fused client
        // update the UI - i.e. set all properties in their associated text view items
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(YakMap.this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            // user provided the permission
            fusedLocationProviderClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    // we got permissions. Put the values of location. XXX into the UI components.
                    updateUIValues(location);
                    Log.d("tag3", "location: " + location);
                }
            });
        }
        else {
            // permissions not granted yet
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSIONS_FINE_LOCATION);
            }
        }
    }

    private void updateUIValues(Location location) {
        // update all of the text view objects with a new location
        Geocoder geocoder = new Geocoder(YakMap.this);

        //LatLng loc = new LatLng(location.getLatitude(), location.getLongitude());
        //map.addMarker(new MarkerOptions().position(loc).title("you"));
        Log.d("tag", "location: " + location);
        try {
            List<Address> adresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
        }
        catch(Exception e) {
            //unable to get street address
        }

    }

    private void locationUpdates() {

    }
    */

}