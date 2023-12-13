package com.example.cloudcomputingproject.datas;

import com.google.gson.annotations.SerializedName;

public class LikePostData {
    @SerializedName("user_id")
    private String user_id;

    public LikePostData(String user_id){
        this.user_id = user_id;
    }

    public void setUserId(String user_id){
        this.user_id = user_id;
    }
    public String getUserId(){
        return this.user_id;
    }
}
