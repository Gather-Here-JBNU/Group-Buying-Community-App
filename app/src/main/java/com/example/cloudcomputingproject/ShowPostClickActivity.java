package com.example.cloudcomputingproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.cloudcomputingproject.datas.LikeData;
import com.example.cloudcomputingproject.datas.LikeDataResponse;
import com.example.cloudcomputingproject.utility.APIInterface;
import com.example.cloudcomputingproject.utility.RetrofitClient;
import com.google.firebase.auth.FirebaseAuth;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShowPostClickActivity extends AppCompatActivity {
    Toolbar toolbar;

    private boolean isFavorite = false;
    ImageView like_iv;
    String post_id, user_id;

    Intent intent;

    private APIInterface service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showpost_click);

        toolbar = findViewById(R.id.toolbar);
        like_iv  = findViewById(R.id.like_iv);

        intent = getIntent();
        post_id = intent.getStringExtra("post_id");
        user_id = FirebaseAuth.getInstance().getUid();
        // post_id와 user_id를 통해 likes 테이블의 정보에 접근, 행의 존재 유무에 따라 isFavorite 상태 변경

        service = RetrofitClient.getClient().create(APIInterface.class); // 서버 연결

        LikeDataControl(new LikeData(user_id, post_id, 0)); // flag 0번으로 select 기능 control
        like_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onStarClick();
            }
        });
    }

    public void onStarClick() {
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
            state = 1; // 1번 state는 db에 추가.
        } else {
            like_iv.setImageResource(R.drawable.like);
            state = 2;
            // 2번 state는 db에 추가.
        }
        updateLikeState(state); // state 전달. update
    }
    private void updateLikeState(int state){
        if(state == 1) { // insert
            LikeDataControl(new LikeData(user_id, post_id, 1));
            // post_id로 likes 테이블에 접근해서, user_id와 post_id 삽입
            // flag 1로 컨트롤
        } else if(state == 2){ // remove
            LikeDataControl(new LikeData(user_id, post_id, 2));
            // post_id와 user_id가 일치하는 행을 삭제
            // flag 2로 컨트롤
        }

    }

    private void LikeDataCheck(LikeData data){

    }

    private void LikeDataControl(LikeData data){
        service.LikeDataControl(data).enqueue(new Callback<LikeDataResponse>() {
            @Override
            public void onResponse(Call<LikeDataResponse> call, Response<LikeDataResponse> response) {
                LikeDataResponse result = response.body();

                if (result.getCode() == 200) {
                    if("exist".equals(result.getMessage())){ // 0번일경우, 검색된 행이 존재하는지 검삭
                        isFavorite = true;
                        like_iv.setImageResource(R.drawable.like_color); // 존재한다면 채워진 하트
                        Log.d("Favor", String.valueOf(isFavorite));
                    }
                    //finish();
                }
            }

            @Override
            public void onFailure(Call<LikeDataResponse> call, Throwable t) {
                Toast.makeText(ShowPostClickActivity.this, "회원가입 에러 발생", Toast.LENGTH_SHORT).show();
                Log.e("like 테이블 컨트롤 실패", t.getMessage());
                t.printStackTrace();
            }
        });
    }
}