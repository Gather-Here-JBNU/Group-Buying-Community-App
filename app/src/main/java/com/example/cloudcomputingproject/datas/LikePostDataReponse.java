package com.example.cloudcomputingproject.datas;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LikePostDataReponse {
    private List<PostItem> posts;

    @SerializedName("code")
    private int code;

    @SerializedName("message")
    private String message;

    public List<PostItem> getPosts() {
        return posts;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public static class PostItem {
        @SerializedName("post_id")
        private String postId;

        @SerializedName("user_id")
        private String userId;

        @SerializedName("title")
        private String title;

        @SerializedName("nickname")
        private String nickname;

        public String getPostId() {
            return postId;
        }

        public String getUserId() {
            return userId;
        }

        public String getTitle() {
            return title;
        }

        public String getNickname() {
            return nickname;
        }
    }
}
