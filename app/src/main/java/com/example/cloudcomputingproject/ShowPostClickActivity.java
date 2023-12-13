package com.example.cloudcomputingproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cloudcomputingproject.datas.MainPostDataGet;
import com.example.cloudcomputingproject.datas.MainPostDataGetResponse;
import com.example.cloudcomputingproject.datas.Post;
import com.example.cloudcomputingproject.datas.PostViewGet;
import com.example.cloudcomputingproject.datas.PostViewGetResponse;
import com.example.cloudcomputingproject.datas.UserDataGetResponse;
import com.example.cloudcomputingproject.utility.APIInterface;
import com.example.cloudcomputingproject.utility.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShowPostClickActivity extends AppCompatActivity {
    private boolean isFavorite = false;
    APIInterface service;
    Toolbar toolbar;
    Intent intent;
    String post_id, title, nickname, info, price, location;
    ImageView like_iv, img_iv;
    TextView nickname_tv, post_info, price_tv, location_tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showpost_click);

        service = RetrofitClient.getClient().create(APIInterface.class); // 서버 연결

        toolbar = findViewById(R.id.toolbar);
        like_iv  = findViewById(R.id.like_iv);

        startGet(new PostViewGet((post_id));

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // 툴바 왼쪽에, 뒤로가기 버튼 추가.
        getSupportActionBar().setTitle(title);


        intent = getIntent();
        post_id = intent.getStringExtra("post_id");

    }

    private void startGet(PostViewGet data) {
        service.PostViewGet(data).enqueue(new Callback<PostViewGetResponse>() {
            @Override
            public void onResponse(Call<PostViewGetResponse> call, Response<PostViewGetResponse> response) {
                PostViewGetResponse result = response.body();

                if (result.getCode() == 200) {
                    Log.e("유저 데이터 불러오기 성공..", String.valueOf("."));

                    title = result.getTitle();       // 제목 가져오기
                    nickname = result.getNickname(); // 닉네임 가져오기.
                    info = result.getNickname(); // 본문 가져오기.
                    price = result.getNickname(); // 가격 가져오기.
                    location = result.getNickname(); // 지역 가져오기.
                    nickname = result.getNickname(); // 닉네임 가져오기.
                    nickname = result.getNickname(); // 닉네임 가져오기.

                }
            }

            @Override
            public void onFailure(Call<PostViewGetResponse> call, Throwable t) {

            }
        });

        public void onStarClick(View view) {
            // 즐겨찾기 토글 전환
            isFavorite = !isFavorite;

            // 버튼 이미지 업데이트
            updateFavoriteUI();
        }
        private void updateFavoriteUI() {
            int state;
            // UI를 업데이트하는 코드
            if (isFavorite) {
                like_iv.setImageResource(R.drawable.like_color);
                // 파이어베이스에 즐겨찾기 정보 추가
                state = 0 ; // 0번 state는 db에 추가.
            } else {
                like_iv.setImageResource(R.drawable.like);
                state = 1;
                // 1번 state는 db에 추가.
            }
            updateLikeState(state);
        }

        private void updateLikeState(int state){

        }
    }
}