package com.example.yaker;

public class Chat {
    String chatMessage;
    int likes;
    boolean willie = false;

    public String getChat(){
        return chatMessage;
    }
    public String getLikes(){

        return String.valueOf(likes);
    }

    public boolean isWillie(){
        return willie;
    }
}
