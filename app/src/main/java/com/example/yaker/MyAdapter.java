package com.example.yaker;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {


    Context context;
    ArrayList<Chat> list;
    DatabaseReference database;
    boolean canUpvote = true;
    public MyAdapter(Context context, ArrayList<Chat> list){
        this.context = context;
        this.list = list;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Chat chat = list.get(position);

        holder.chatMessage.setText(chat.getChat());
        holder.likesText.setText(chat.getLikes());
        holder.locationView.setText(chat.getLocation());

        if (chat.isWillie()){
            holder.willieImage.setVisibility(View.VISIBLE);
        }
        holder.downvote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                database = FirebaseDatabase.getInstance().getReference("Messages");
                database.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (holder.downvote.isEnabled()) {
                            database.child(chat.getID()).child("likes").setValue((Integer.parseInt(snapshot.child(chat.getID()).child("likes").getValue().toString()) - 1));
                            holder.downvote.setEnabled(false);
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
        holder.upvote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                database = FirebaseDatabase.getInstance().getReference("Messages");
                database.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (holder.upvote.isEnabled()) {
                                database.child(chat.getID()).child("likes").setValue((Integer.parseInt(snapshot.child(chat.getID()).child("likes").getValue().toString()) + 1));
                                holder.upvote.setEnabled(false);
                            }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView chatMessage,likesText,locationView;
        ImageView willieImage;
        ImageButton upvote,downvote;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            locationView = itemView.findViewById(R.id.locationView);
            upvote = itemView.findViewById(R.id.upvoteBtn);
            downvote = itemView.findViewById(R.id.downvoteBtn);
            chatMessage = itemView.findViewById(R.id.ChatText);
            likesText = itemView.findViewById(R.id.likesText);
            willieImage = itemView.findViewById(R.id.willyImage);

        }
    }
}
