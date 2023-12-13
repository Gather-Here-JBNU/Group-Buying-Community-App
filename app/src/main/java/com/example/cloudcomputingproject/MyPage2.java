package com.example.cloudcomputingproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cloudcomputingproject.datas.UserDataGet;
import com.example.cloudcomputingproject.datas.UserDataGetResponse;
import com.example.cloudcomputingproject.utility.APIInterface;
import com.example.cloudcomputingproject.utility.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyPage2 extends AppCompatActivity {

    Intent intent;
    String user_id;
    APIInterface service;

    String nickname, info;

    TextView nickname_tv, selfintro_tv;
    ProgressBar progressBar;
    Toolbar toolbar;

    TableRow view_post_tr, favorites_tr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_page2);

        intent = getIntent();
        user_id = intent.getStringExtra("user_id");

        service = RetrofitClient.getClient().create(APIInterface.class);
        nickname_tv = findViewById(R.id.nickname_tv);
        selfintro_tv = findViewById(R.id.selfintro_tv);
        view_post_tr = findViewById(R.id.view_post_tr); // 작성한 소식
        favorites_tr = findViewById(R.id.favorites_tr); // 즐겨찾기 테이블

        progressBar = findViewById(R.id.progressBar);
        animateProgressBar(36); // 원하는 프로그래스 값으로 애니메이션 실행

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // 툴바 왼쪽에, 뒤로가기 버튼 추가.

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        view_post_tr.setOnClickListener(new View.OnClickListener() { // 작성한 소식 클릭시, 액티비티 이동
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyPage2.this, ViewPostActivity.class);
                intent.putExtra("user_id", user_id);
                startActivity(intent);
            }
        });


        favorites_tr.setOnClickListener(new View.OnClickListener() { //  관심목록 클릭시, 액티비티 이동
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyPage2.this, MyLikesActivity.class);
                intent.putExtra("user_id", user_id);
                startActivity(intent);
            }
        });

        startGet(new UserDataGet(user_id));
    }

    private void startGet(UserDataGet data){ // 입력된 데이터, u_id를바탕으로 통신할 것임.
        service.UserGet(data).enqueue(new Callback<UserDataGetResponse>() {
            @Override
            public void onResponse(Call<UserDataGetResponse> call, Response<UserDataGetResponse> response) {
                UserDataGetResponse result = response.body();
                // 성공시, result에 정보를 불러올 것임. 여기서 result에 대한 정보는 UserDataGetRespons.java에 명시되어 있음.

                if (result.getCode() == 200) {
                    Log.e("유저 데이터 불러오기 성공..", String.valueOf("."));
                    nickname = result.getNickname(); // 닉네임 가져오기.
                    info = result.getInfo();       // 자기소개 가져오기

                    nickname_tv.setText(nickname);
                    selfintro_tv.setText(info);
                }
            }


            @Override
            public void onFailure(Call<UserDataGetResponse> call, Throwable t) {
                Toast.makeText(MyPage2.this, "유저 정보 가져오기", Toast.LENGTH_SHORT).show();
                Log.e("유저 데이터 불러오기 실패", t.getMessage());
                t.printStackTrace();
            }
        });

    }
    private void animateProgressBar(int targetProgress) {
        ValueAnimator animator = ValueAnimator.ofInt(0, targetProgress);
        animator.setDuration(500); // 애니메이션 기간 (1초로 설정, 필요에 따라 조절)

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int progress = (int) animation.getAnimatedValue();
                progressBar.setProgress(progress);
            }
        });

        animator.start();
    }

}