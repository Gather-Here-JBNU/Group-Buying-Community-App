package com.example.cloudcomputingproject.postlist;

public class PostListPreview {
    private String title;
    private String nickname;

    private String post_id;
    private String img;

    public PostListPreview(String post_id, String nickname, String title, String img) {
        this.post_id = post_id;
        this.nickname= nickname;
        this.title = title;
        this.img = img;
    }

    // Getters and Setters
    public void setTitle(String title){this.title = title;}
    public String getTitle() {return this.title;}

    public void setNickname(String title){this.nickname = nickname;}
    public String getNickname(){ return this.nickname; }

    public void setPostId(String post_id){this.post_id = post_id;}
    public String getPost_id() {return this.post_id;}

    public void setImg(String img){this.img = img;}
    public String getImg() { return this.img;}
}