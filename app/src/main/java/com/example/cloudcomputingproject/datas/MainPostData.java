package com.example.cloudcomputingproject.datas;

import com.google.gson.annotations.SerializedName;

public class MainPostData {
    @SerializedName("category_label")
    private String category_label;

    public MainPostData(String category_label){
        this.category_label = category_label;
    }
}
