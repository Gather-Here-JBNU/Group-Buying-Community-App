package com.example.cloudcomputingproject.datas;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CategoryDataResponse {
    @SerializedName("category_label")
    private List<String> category_label;

    @SerializedName("code")
    private int code;
    @SerializedName("message")
    private String message;

    public List<String> getCategoryLabel(){ return category_label; }

    public int getCode(){
        return code;
    }

    public String getMessage(){ return message; }
}
