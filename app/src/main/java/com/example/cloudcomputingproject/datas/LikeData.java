package com.example.cloudcomputingproject.datas;

import com.google.gson.annotations.SerializedName;

public class LikeData {
    @SerializedName("user_id")
    private String user_id;

    @SerializedName("post_id")
    private String post_id;

    @SerializedName("flag")
    private int flag;

    public LikeData(String user_id, String post_id, int flag){
        this.user_id = user_id;
        this.post_id = post_id;
        this.flag = flag;
    }
}
