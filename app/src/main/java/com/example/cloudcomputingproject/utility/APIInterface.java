package com.example.cloudcomputingproject.utility;

import com.example.cloudcomputingproject.datas.CategoryDataResponse;
import com.example.cloudcomputingproject.datas.PostDataPut;
import com.example.cloudcomputingproject.datas.PostDataPutResponse;
import com.example.cloudcomputingproject.datas.UserDataGet;
import com.example.cloudcomputingproject.datas.UserDataGetResponse;
import com.example.cloudcomputingproject.datas.UserDataInsert;
import com.example.cloudcomputingproject.datas.UserDataInsertResponse;
import com.example.cloudcomputingproject.datas.UserDataUpdate;
import com.example.cloudcomputingproject.datas.UserDataUpdateResponse;

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

    @POST("/user/get")
    Call<UserDataGetResponse> UserGet(@Body UserDataGet data);

    @POST("/user/update")
    Call<UserDataUpdateResponse> UserUpdate(@Body UserDataUpdate data);
}
