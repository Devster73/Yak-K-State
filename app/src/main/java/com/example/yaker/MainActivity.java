package com.example.yaker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent a = new Intent(this, ChatList.class);
        startActivity(a);
        // Write a message to the database
        /* FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("messages");


        EditText txt = (EditText) findViewById(R.id.editTextTextPersonName);
        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {final int min = 20;
                final int max = 80;
                final int random = new Random().nextInt((max - min) + 1) + min;

                myRef.push().setValue(txt.getText().toString());
                txt.setText("");
            }
        });
        */

    }
}