package com.example.cloudcomputingproject.datas;

import com.google.gson.annotations.SerializedName;

public class MainPostDataGetResponse {
    @SerializedName("post_id")
    private String post_id;

    @SerializedName("nickname")
    private String nickname;

    @SerializedName("title")
    private String title;

    @SerializedName("img")
    private String img; // Uri로 변환해서 써야할 수도 있음.


    @SerializedName("code")
    private int code;

    @SerializedName("message")
    private String message;

    public String getPost_id(){
        return post_id;
    }

    public String getNickname(){
        return nickname;
    }

    public String getTitle(){
        return title;
    }

    public String getImg(){
        return img;
    }


    public int getCode(){
        return code;
    }

    public String getMessage(){
        return message;
    }

}
