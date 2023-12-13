package com.example.cloudcomputingproject.datas;

import com.google.gson.annotations.SerializedName;

public class PostViewGet {
    @SerializedName("post_id")
    private String post_id;

    public PostViewGet(String post_id){
        this.post_id = post_id;
    }
}