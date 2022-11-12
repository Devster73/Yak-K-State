package com.example.yaker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Collections;

public class ChatList extends AppCompatActivity {

    RecyclerView recyclerView;
    DatabaseReference database;
    MyAdapter myAdapter;
    ArrayList<Chat> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_list);

        recyclerView = findViewById(R.id.chatList);
        database = FirebaseDatabase.getInstance().getReference("Messages");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        myAdapter = new MyAdapter(this,list);
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
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                Log.d("mia",snapshot.getChildrenCount() + " OLD");
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){

                    Chat chat = new Chat();
                    chat.chatMessage = dataSnapshot.child("chat").getValue().toString();
                    chat.likes = Integer.parseInt(dataSnapshot.child("likes").getValue().toString());

                    Log.d("mia" , chat.chatMessage + " MSG");
                    list.add(chat);

                }
                Collections.reverse(list);
                myAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}