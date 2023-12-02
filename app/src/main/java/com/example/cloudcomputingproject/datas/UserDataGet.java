package com.example.cloudcomputingproject.datas;

import com.google.gson.annotations.SerializedName;

public class UserDataGet { // 유저의 UID를 보내, user 테이블의 정보들을 불러올 것이다.
    @SerializedName("user_id")
    private String user_id;

    public UserDataGet(String user_id){
        this.user_id = user_id;
    }
}
