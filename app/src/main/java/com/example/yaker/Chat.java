package com.example.yaker;

import java.util.List;

public class Chat {
    String chatMessage;
    List<String> comments;
    int likes;

    public String getChat(){
        return chatMessage;
    }
    public String getLikes() { return String.valueOf(likes); }
    public List<String> getComments() { return comments; }
}
