package com.example.cloudcomputingproject.datas;

import com.google.gson.annotations.SerializedName;

public class MainPostDataGet { // 카테고리를 정보를 서버에게 보내고, response를 통해 post 테이블의 정보를 가져올 것임.
    @SerializedName("category_label")
    private String category_label;

    public MainPostDataGet(String category_label){
        this.category_label = category_label;
    }
}
