package com.example.cloudcomputingproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TableRow;
import android.widget.Toast;

public class Mypage extends AppCompatActivity {

    ProgressBar progressBar;
    Button changeButton;
    TableRow view_post_tr, view_comment_tr, favorites_tr, logout_tr;
    Toolbar toolbar;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage);
        progressBar = findViewById(R.id.progressBar);
        animateProgressBar(36); // 원하는 프로그래스 값으로 애니메이션 실행

        changeButton = findViewById(R.id.profileChange_btn); // 프로필 수정 버튼

        view_post_tr = findViewById(R.id.view_post_tr); // 내가 작성한 소식 테이블
        view_comment_tr = findViewById(R.id.view_comment_tr); // 내가 남긴 댓글 테이블
        favorites_tr = findViewById(R.id.favorites_tr); // 즐겨찾기 테이블
        logout_tr = findViewById(R.id.logout_tr); // 로그아웃 테이블

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // 툴바 왼쪽에, 뒤로가기 버튼 추가.
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

        view_comment_tr.setOnClickListener(new View.OnClickListener() { // 내가 남긴 댓글 클릭시, 액티비티 이동
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Mypage.this, ViewCommentActivity.class);
                startActivity(intent);
            }
        });

        favorites_tr.setOnClickListener(new View.OnClickListener() { //  관심목록 클릭시, 액티비티 이동
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Mypage.this, MyFavoritesActivity.class);
                startActivity(intent);
            }
        });

        logout_tr.setOnClickListener(new View.OnClickListener() { // 로그아웃 클릭시,
            @Override
            public void onClick(View v) {
                Toast.makeText(Mypage.this, "click", Toast.LENGTH_SHORT).show();
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