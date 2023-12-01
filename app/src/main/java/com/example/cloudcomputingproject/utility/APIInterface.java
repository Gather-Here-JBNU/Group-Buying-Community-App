package com.example.cloudcomputingproject.utility;

import com.example.cloudcomputingproject.datas.PutPostData;
import com.example.cloudcomputingproject.datas.PutPostDataResponse;
import com.example.cloudcomputingproject.datas.UserDataInsert;
import com.example.cloudcomputingproject.datas.UserDataInsertResponse;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface APIInterface {
    @POST("/user/add")
    Call<UserDataInsertResponse> userLoginInsert(@Body UserDataInsert data);

    @POST("/post/add")
    Call<PutPostDataResponse> PostInsert(@Body PutPostData data);
}
