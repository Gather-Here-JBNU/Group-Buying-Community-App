package com.example.cloudcomputingproject.datas;

import com.google.gson.annotations.SerializedName;

public class PostItem {
    @SerializedName("id")
    private String post_id;

    @SerializedName("nickname")
    private String nickname;

    @SerializedName("title")
    private String title;

    @SerializedName("img")
    private String img;

    public PostItem(String post_id, String nickname, String title, String img){
        this.post_id =post_id;
        this.nickname = nickname;
        this.title = title;
        this.img = img;
    }

    public String getPostId() {
        return post_id;
    }


    public String getNickname() {
        return nickname;
    }

    public String getTitle() {
        return title;
    }

    public String getImg() { return img;}
}

