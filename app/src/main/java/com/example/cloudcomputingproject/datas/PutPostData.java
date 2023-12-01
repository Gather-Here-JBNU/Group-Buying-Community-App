package com.example.cloudcomputingproject.datas;

import android.util.Log;

import com.google.gson.annotations.SerializedName;

public class PutPostData {
    @SerializedName("user_id")
    private String user_id;

    @SerializedName("title")
    private String title;

    @SerializedName("contents")
    private String contents;


    @SerializedName("img")
    private String img;

    @SerializedName("category_label")
    private String category_label;

    @SerializedName("price")
    private String price;

    @SerializedName("location")
    private String location;

    public PutPostData(String user_id, String title, String contents, String img, String category_label,
                       String price, String location){
        this.user_id = user_id;
        this.title = title;
        this.contents = contents;
        this.img = img;
        this.category_label = category_label;
        this.price = price;
        this.location = location;
    }
    public void PutPostDataPrint(){
        Log.d("this.user_id : ", this.user_id);
        Log.d("this.title : ", this.title);
        Log.d("this.contents : ", this.contents);
        Log.d("this.img : ", this.img);
        Log.d("this.category_label: ", this.category_label);
        Log.d("this.price : ", this.price);
        Log.d("this.location : ", this.location);
    }
}
