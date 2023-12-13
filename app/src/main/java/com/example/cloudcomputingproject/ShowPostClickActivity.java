package com.example.cloudcomputingproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class ShowPostClickActivity extends AppCompatActivity {
    Toolbar toolbar;

    private boolean isFavorite = false;
    ImageView like_iv;
    String post_id;

    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showpost_click);

        toolbar = findViewById(R.id.toolbar);
        like_iv  = findViewById(R.id.like_iv);

        intent = getIntent();
        post_id = intent.getStringExtra("post_id");
    }

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