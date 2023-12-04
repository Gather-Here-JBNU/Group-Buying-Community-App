package com.example.cloudcomputingproject.datas;

import com.google.gson.annotations.SerializedName;

public class UserDataUpdateResponse {
    @SerializedName("nickname")
    private String nickname;

    @SerializedName("info")
    private String info;

    @SerializedName("code")
    private int code;

    @SerializedName("message")
    private String message;
    public String getNickname(){
        return nickname;
    }
    public String getInfo(){
        return info;
    }

    public int getCode(){
        return code;
    }

    public String getMessage(){
        return message;
    }
}
