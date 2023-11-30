package com.example.cloudcomputingproject.datas;

import com.google.gson.annotations.SerializedName;

public class UserDataInsertResponse {

    @SerializedName("code")
    private int code;

    @SerializedName("message")
    private String message;

    public int getCode(){
        return code;
    }

    public String getMessage(){
        return message;
    }
}