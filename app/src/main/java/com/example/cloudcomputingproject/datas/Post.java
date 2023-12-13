package com.example.cloudcomputingproject.datas;

import com.google.gson.annotations.SerializedName;

public class Post{
    @SerializedName("id")
    public String post_id;

    @SerializedName("nickname")
    public String nickname;

    @SerializedName("title")
    public String title;
    
    @SerializedName("img")
    public String img; // Uri로 변환해서 써야할 수도 있음.
}
