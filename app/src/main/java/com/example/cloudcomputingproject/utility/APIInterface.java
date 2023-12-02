package com.example.cloudcomputingproject.utility;

import com.example.cloudcomputingproject.datas.CategoryDataResponse;
import com.example.cloudcomputingproject.datas.PostDataPut;
import com.example.cloudcomputingproject.datas.PostDataPutResponse;
import com.example.cloudcomputingproject.datas.UserDataInsert;
import com.example.cloudcomputingproject.datas.UserDataInsertResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface APIInterface {
    @POST("/user/add")
    Call<UserDataInsertResponse> userLoginInsert(@Body UserDataInsert data);

    @POST("/post/add")
    Call<PostDataPutResponse> PostInsert(@Body PostDataPut data);

    @POST("/category/get")
    Call<CategoryDataResponse> CategoryGet();
}
