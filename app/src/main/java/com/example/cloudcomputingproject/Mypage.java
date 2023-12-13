package com.example.cloudcomputingproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.cloudcomputingproject.datas.UserDataGet;
import com.example.cloudcomputingproject.datas.UserDataGetResponse;
import com.example.cloudcomputingproject.utility.APIInterface;
import com.example.cloudcomputingproject.utility.RetrofitClient;
import com.google.firebase.auth.FirebaseAuth;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Mypage extends AppCompatActivity {
    String user_id, nickname;
    Intent intent;
    ProgressBar progressBar;
    Button changeButton;
    TableRow view_post_tr, favorites_tr, logout_tr;
    Toolbar toolbar;
    APIInterface service;

    TextView nickname_tv;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage);

        intent = getIntent();
        user_id = FirebaseAuth.getInstance().getUid();

        progressBar = findViewById(R.id.progressBar);
        animateProgressBar(36); // 원하는 프로그래스 값으로 애니메이션 실행

        changeButton = findViewById(R.id.profileChange_btn); // 프로필 수정 버튼

        view_post_tr = findViewById(R.id.view_post_tr); // 내가 작성한 소식 테이블

        favorites_tr = findViewById(R.id.favorites_tr); // 즐겨찾기 테이블
        logout_tr = findViewById(R.id.logout_tr); // 로그아웃 테이블
        nickname_tv = findViewById(R.id.nickname_tv);
        service = RetrofitClient.getClient().create(APIInterface.class); // 서버 연결

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // 툴바 왼쪽에, 뒤로가기 버튼 추가.

        startGet(new UserDataGet(user_id));
        changeButton.setOnClickListener(new View.OnClickListener(){  // 프로필 수정 버튼 클릭 시, 액티비티 이동
            public void onClick(View v){
                Intent intent = new Intent(Mypage.this, ProfileChange.class);
                startActivity(intent);
            }
        });

        view_post_tr.setOnClickListener(new View.OnClickListener() { // 내가 작성한 소식 클릭시, 액티비티 이동
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Mypage.this, ViewPostActivity.class);
                startActivity(intent);
            }
        });


        favorites_tr.setOnClickListener(new View.OnClickListener() { //  관심목록 클릭시, 액티비티 이동
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Mypage.this, MyLikesActivity.class);
                startActivity(intent);
            }
        });

        logout_tr.setOnClickListener(new View.OnClickListener() { // 로그아웃 클릭시,
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                finish();

                //Intent를 새로 만든 후 LoginActivity로 화면전환
                Intent intent = new Intent(Mypage.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
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

    private void startGet(UserDataGet data){ // 입력된 데이터, u_id를바탕으로 통신할 것임.
        service.UserGet(data).enqueue(new Callback<UserDataGetResponse>() {
            @Override
            public void onResponse(Call<UserDataGetResponse> call, Response<UserDataGetResponse> response) {
                UserDataGetResponse result = response.body();
                // 성공시, result에 정보를 불러올 것임. 여기서 result에 대한 정보는 UserDataGetRespons.java에 명시되어 있음.

                if (result.getCode() == 200) {
                    Log.e("유저 데이터 불러오기 성공..", String.valueOf("."));

                    nickname = result.getNickname(); // 닉네임 가져오기.

                    nickname_tv.setText(nickname);
                }
            }

            @Override
            public void onFailure(Call<UserDataGetResponse> call, Throwable t) {
                Log.e("유저 데이터 불러오기 실패", t.getMessage());
                t.printStackTrace();
            }
        });

    }

    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(Mypage.this, PostActivity.class);
        startActivity(intent);
        finish();
    }
}