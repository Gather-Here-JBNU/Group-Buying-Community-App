package com.example.cloudcomputingproject.postlist;

public class PostPreview {
    private String title;
    private String nickname;

    private String post_id;

    public PostPreview(String title, String nickname, String post_id) {
        this.title = title;
        this.nickname= nickname;
        this.post_id = post_id;
    }

    // Getters and Setters
    public void setTitle(String title){this.title = title;}
    public String getTitle() {return this.title;}

    public void setNickname(String title){this.nickname = nickname;}
    public String getNickname(){ return this.nickname; }

    public void setPostId(String post_id){this.post_id = post_id;}
    public String getPost_id() {return this.post_id;}
}