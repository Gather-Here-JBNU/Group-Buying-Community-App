package com.example.cloudcomputingproject.postpage;

public class PostPreview {
    private String imageUrl;
    private String title;
    private String summary;

    public PostPreview(String imageUrl, String title, String summary) {
        this.imageUrl = imageUrl;
        this.title = title;
        this.summary = summary;
    }

    // Getters and Setters
    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
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

    public void setSummary(String summary) {
        this.summary = summary;
    }
}