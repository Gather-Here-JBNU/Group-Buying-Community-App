package com.example.cloudcomputingproject.datas;

import com.google.gson.annotations.SerializedName;

public class LikePostData {
    @SerializedName("user_id")
    private String user_id;

    @SerializedName("flag")
    private String flag;

    public LikePostData(String user_id, String flag){
        this.user_id = user_id;
        this.flag = flag;
    }

    public void setUserId(String user_id){
        this.user_id = user_id;
    }
    public String getUserId(){
        return this.user_id;
    }

    public void setFlag(String flag) {this.flag = flag;}
    public String getFlag(){return this.flag;}
}
