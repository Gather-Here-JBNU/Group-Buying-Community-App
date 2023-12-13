package com.example.cloudcomputingproject.datas;

import com.google.gson.annotations.SerializedName;

public class PostViewGetResponse {
    @SerializedName("title")
    private String title;
    @SerializedName("nickname")
    private String nickname;
    @SerializedName("img")
    private String img;
    @SerializedName("contents")
    private String contents;
    @SerializedName("price")
    private String price;
    @SerializedName("location")
    private String location;
    @SerializedName("code")
    private int code;
    @SerializedName("message")
    private String message;

    public String getTitle(){
        return title;
    }
    public String getNickname(){
        return nickname;
    }
    public String getImg(){
        return img;
    }
    public String getContents(){
        return contents;
    }
    public String getPrice(){
        return price;
    }
    public String getLocation(){
        return location;
    }
    public int getCode(){
        return code;
    }
    public String getMessage(){
        return message;
    }
}