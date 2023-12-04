package com.example.cloudcomputingproject.datas;

import com.google.gson.annotations.SerializedName;

public class UserDataUpdate { // 회원가입시 EditText에 적은 내용을, user 테이블에 insert 할 것이다.
    @SerializedName("user_id")
    private String user_id;
    @SerializedName("user_nickname")
    private String user_nickname;
    @SerializedName("user_info")
    private String user_info;

    public UserDataUpdate(String user_id, String user_nickname, String user_info){
        this.user_id = user_id;
        this.user_nickname = user_nickname;
        this.user_info = user_info;
    }
}