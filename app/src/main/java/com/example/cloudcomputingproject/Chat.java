package com.example.cloudcomputingproject;

import java.util.HashMap;
import java.util.Map;

public class Chat {
    String email;
    String nickname;
    String text;
    String time;

    public Chat(){
    }

    public Chat(String email, String nickname, String text, String time){
        this.email = email;
        this.nickname = nickname;
        this.text = text;
        this.time = time;
    }

    public Map<String, String> toMap(){
        Map<String, String> map = new HashMap<>();
        map.put("email", email);
        map.put("nickname", nickname);
        map.put("text", text);
        map.put("time", time);
        return map;
    }

    public String getEmail(){
        return email;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public String getNickname(){
        return nickname;
    }

    public void setNickname(String nickname){
        this.nickname = nickname;
    }

    public String getText(){
        return text;
    }

    public void setText(String text){
        this.text = text;
    }

    public String getTime(){
        return time;
    }

    public void setTime(String time){
        this.time = time;
    }
}
