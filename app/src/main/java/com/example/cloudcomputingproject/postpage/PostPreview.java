package com.example.cloudcomputingproject.postpage;

public class PostPreview {
    private String profileImageUrl;
    private String userName;
    private String postImageUrl;
    private String title;
    private String summary;

    public PostPreview(String profileImageUrl, String userName, String postImageUrl, String title) {

        this.profileImageUrl = profileImageUrl;
        this.userName = userName;
        this.postImageUrl = postImageUrl;
        this.title = title;
    }

    // Getters and Setters
    public String getPostImageUrl() {
        return postImageUrl;
    }
    public void setPostImageUrl(String postImageUrl) {
        this.postImageUrl = postImageUrl;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }
    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}