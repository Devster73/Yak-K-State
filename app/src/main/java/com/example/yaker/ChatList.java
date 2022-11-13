package com.example.yaker;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

public class ChatList extends AppCompatActivity {

    RecyclerView recyclerView;
    DatabaseReference database;

    MyAdapter myAdapter;
    ArrayList<Chat> list;

    private LocationManager locationManager;
    GoogleMap map;
    Location currLocation;
    String locationName;

    Map<String, LatLngBounds> boundaries = new HashMap<String, LatLngBounds>() {{
        put("Business Building", new LatLngBounds(new LatLng(39.188513180740, -96.578158845604), new LatLng(39.189643351576, -96.576490947163)));
        put("Engineering Building", new LatLngBounds(new LatLng(39.189678019132, -96.585366259050), new LatLng(39.191050058896, -96.583563814667)));
        put("Hale Library", new LatLngBounds(new LatLng(39.190305834599, -96.581375132030), new LatLng(39.191020955309, -96.579787264360)));
        put("City Park", new LatLngBounds(new LatLng(39.179578076694, -96.578309794799), new LatLng(39.183641194104, -96.573188558799)));
        put("Aggieville", new LatLngBounds(new LatLng(39.184463129588, -96.576851748801), new LatLng(39.186723402440, -96.573212658749)));
        put("Manhattan Hill", new LatLngBounds(new LatLng(39.191755631581, -96.565286728193), new LatLng(39.193107445387, -96.561989364600)));
        put("Bill Snyder Stadium", new LatLngBounds(new LatLng(39.199771727230, -96.59850668169), new LatLng(39.204069934874, -96.58963867511)));
    }


    }

    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_list);
        mAuth = FirebaseAuth.getInstance();


        FirebaseUser currentUser = mAuth.getCurrentUser();
        mAuth.signInAnonymously();



        recyclerView = findViewById(R.id.chatList);
        database = FirebaseDatabase.getInstance().getReference("Messages");

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        myAdapter = new MyAdapter(this, list);
        recyclerView.setAdapter(myAdapter);


        Button sendButton = (Button) findViewById(R.id.sendChat);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView chatMessage = findViewById(R.id.messageChat);

                Log.d("mia","mia");
                Chat chat = new Chat();
                chat.likes = 3;

                chat.chatMessage = chatMessage.getText().toString();

                database.push().setValue(chat);
            }
        });

        ImageButton mapButton = (ImageButton) findViewById(R.id.mapButton);
        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               gotoMap();
            }
        });

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();

                Chat c = new Chat();
                c.chatMessage = snapshot.child("Willie").child("chat").getValue().toString();
                c.willie = true;

                for (DataSnapshot dataSnapshot : snapshot.getChildren()){


                    if (!dataSnapshot.getKey().equals("Willie")){
                        Log.d("TAG",dataSnapshot.getKey());
                        Chat chat = new Chat();
                        chat.chatMessage = dataSnapshot.child("chat").getValue().toString();
                        chat.likes = Integer.parseInt(dataSnapshot.child("likes").getValue().toString());
                        chat.ID = dataSnapshot.getKey();
                        Log.d("mia" , chat.chatMessage + " MSG");
                        list.add(chat);
                    }


                }

                list.add(c);
                Collections.reverse(list);
                myAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void findLocation(Location location, Dictionary<String, String> dict, ArrayList<LatLng> buildingList) {
        // track location
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        if (ContextCompat.checkSelfPermission(ChatList.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(ChatList.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(ChatList.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10, 1, new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                currLocation = location;
            }
        });
        if (location != null) {
            LatLng coord = new LatLng(location.getLatitude(), location.getLongitude());

            final int[] result = {0};
                boundaries.forEach((key, value) -> {
                    if(value.contains(coord)) {
                        locationName = key;
                        result[0] = 1;
                    }
                });
            if (result[0] == 0) {
                locationName = "Somewhere in the world...";
            }
        }
        else
            locationName = "Somewhere in the world";

    }

    public void  gotoMap(){
        Intent a = new Intent(this, YakMap.class);
        startActivity(a);
    }





}