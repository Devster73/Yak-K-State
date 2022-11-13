package com.example.yaker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {


    Context context;
    ArrayList<Chat> list;


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
        if (chat.isWillie()){
            holder.willieImage.setVisibility(View.VISIBLE);
        }
        holder.upvote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView chatMessage,likesText;
        ImageView willieImage;
        ImageButton upvote,downvote;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            upvote = itemView.findViewById(R.id.upvoteBtn);
            downvote = itemView.findViewById(R.id.downvoteBtn);
            chatMessage = itemView.findViewById(R.id.ChatText);
            likesText = itemView.findViewById(R.id.likesText);
            willieImage = itemView.findViewById(R.id.willyImage);

        }
    }
}
