package com.example.cloudcomputingproject.utility;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    //private static final String BASE_URL = "http://34.201.194.189:3000/"; //aws
    private static final String BASE_URL = "http://10.55.44.153:3000/"; //로컬
    //private static final String BASE_URL = "http://Group-Buying-Community-App-ALB-1311762057.us-east-1.elb.amazonaws.com:3000/"; //aws 로드 밸런싱
    private static Retrofit retrofit = null;

    private RetrofitClient(){
    }

    public static Retrofit getClient(){
        if(retrofit==null){
            retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL) // 요청을 보낼 base url을 설정한다.
                .addConverterFactory(GsonConverterFactory.create()) // JSON 파싱을 위한 GsonConverterFactory를 추가한다.
                .build();
    }
        return retrofit;
    }
}
