package com.example.cloudcomputingproject.datas;

import com.google.gson.annotations.SerializedName;

public class UserDataInsert { // 회원가입시 EditText에 적은 내용을, user 테이블에 insert 할 것이다.
    @SerializedName("user_id")
    private String user_id;
    @SerializedName("user_email")
    private String user_email;
    @SerializedName("user_pw")
    private String user_pw;
    @SerializedName("user_nickname")
    private String user_nickname;

    public UserDataInsert(String user_id, String user_email, String user_pw, String user_nickname){
        this.user_id = user_id;
        this.user_email = user_email;
        this.user_pw = user_pw;
        this.user_nickname = user_nickname;
    }
}
