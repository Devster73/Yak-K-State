package com.example.yaker;

import java.util.ArrayList;

public class Chat {
    String chatMessage;
    int likes;
    boolean willie = false;
    String location;
    String ID;
    ArrayList<String> comments;
    public String getLocation(){
        return location;
    }

    public String getChat(){
        return chatMessage;
    }
    public String getLikes(){

        return String.valueOf(likes);
    }
    public ArrayList<String> getComments()
    {

        return comments;
    }
    public String getID(){
        return ID;
    }
    public boolean isWillie(){
        return willie;
    }
}
