package com.example.cloudcomputingproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cloudcomputingproject.datas.LikeData;
import com.example.cloudcomputingproject.datas.LikeDataResponse;
import com.example.cloudcomputingproject.datas.PostViewGet;
import com.example.cloudcomputingproject.datas.PostViewGetResponse;
import com.example.cloudcomputingproject.datas.UserDataGet;
import com.example.cloudcomputingproject.datas.UserDataGetResponse;
import com.example.cloudcomputingproject.utility.APIInterface;
import com.example.cloudcomputingproject.utility.RetrofitClient;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShowPostClickActivity extends AppCompatActivity {
    Toolbar toolbar;

    private boolean isFavorite = false;
    ImageView like_iv, img_iv;
    String post_id, title, nickname, info, price, location, user_id, email,
            cur_nickname, img, contents;

    TextView nickname_tv, contents_tv, price_tv, location_tv;

    Intent intent;

    Button EnterChat_btn;
    private APIInterface service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showpost_click);

        toolbar = findViewById(R.id.toolbar);
        like_iv  = findViewById(R.id.like_iv);
        img_iv = findViewById(R.id.img_iv);
        nickname_tv = findViewById(R.id.nickname_tv);
        contents_tv = findViewById(R.id.contents_tv);
        price_tv = findViewById(R.id.price_tv);
        location_tv = findViewById(R.id.location_tv);


        intent = getIntent();
        post_id = intent.getStringExtra("post_id");
        user_id = FirebaseAuth.getInstance().getUid();
        // post_id와 user_id를 통해 likes 테이블의 정보에 접근, 행의 존재 유무에 따라 isFavorite 상태 변경

        EnterChat_btn = findViewById(R.id.EnterChat_btn);

        service = RetrofitClient.getClient().create(APIInterface.class); // 서버 연결

        startGet(new PostViewGet(post_id));
        startUserGet(new UserDataGet(user_id));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // 툴바 왼쪽에, 뒤로가기 버튼 추가.
        getSupportActionBar().setTitle(title);

        LikeDataControl(new LikeData(user_id, post_id, 0)); // flag 0번으로 select 기능 control
        like_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onStarClick();
            }
        });

        EnterChat_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(ShowPostClickActivity.this, ChatActivity.class);
                in.putExtra("email", email);
                in.putExtra("title", title);
                startActivity(in);
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
                Toast.makeText(ShowPostClickActivity.this, "like 테이블 컨트롤 실패", Toast.LENGTH_SHORT).show();
                Log.e("like 테이블 컨트롤 실패", t.getMessage());
                t.printStackTrace();
            }
        });
    }

    private void startGet(PostViewGet data) {
        service.PostViewGet(data).enqueue(new Callback<PostViewGetResponse>() {
            @Override
            public void onResponse(Call<PostViewGetResponse> call, Response<PostViewGetResponse> response) {
                PostViewGetResponse result = response.body();
                Log.d("만세","만세");

                if (result.getCode() == 200) {
                    Log.e("유저 데이터 불러오기 성공..", String.valueOf("."));

                    title = result.getTitle();       // 제목 가져오기
                    nickname = result.getNickname(); // 닉네임 가져오기.
                    img = result.getImg();
                    contents = result.getContents(); // 본문 가져오기.
                    price = result.getPrice(); // 가격 가져오기.
                    location = result.getLocation(); // 지역 가져오기.

                    getSupportActionBar().setTitle(title);
                    nickname_tv.setText(nickname);
                    contents_tv.setText(contents);
                    price_tv.setText("가격 : "+price);
                    location_tv.setText("장소 : "+location);
                    Picasso.get()
                            .load(img)
                            .into(img_iv);



                }
            }

            @Override
            public void onFailure(Call<PostViewGetResponse> call, Throwable t) {
                Toast.makeText(ShowPostClickActivity.this, "포스트 불러오기 오류 발생", Toast.LENGTH_SHORT).show();
                Log.e("포스트 불러오기 실패", t.getMessage());
                t.printStackTrace();
            }
        });
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) { // 툴바의 뒤로가기 버튼이 눌렸을 때
            onBackPressed(); // 현재 액티비티를 종료하고 이전 액티비티로 돌아갑니다.
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private void startUserGet(UserDataGet data){ // 입력된 데이터, u_id를바탕으로 통신할 것임.
        service.UserGet(data).enqueue(new Callback<UserDataGetResponse>() {
            @Override
            public void onResponse(Call<UserDataGetResponse> call, Response<UserDataGetResponse> response) {
                UserDataGetResponse result = response.body();
                // 성공시, result에 정보를 불러올 것임. 여기서 result에 대한 정보는 UserDataGetRespons.java에 명시되어 있음.

                if (result.getCode() == 200) {
                    Log.e("유저 데이터 불러오기 성공..", String.valueOf("."));

                    email = result.getEmail();       // 이메일 가져오기
                    cur_nickname = result.getNickname(); // 현재 유저의 닉네임 가져오기.
                    //info = result.getInfo();       // 자기소개 가져오기 -> 필요없으면 쓰지 않으면 됨.
                }
            }


            @Override
            public void onFailure(Call<UserDataGetResponse> call, Throwable t) {
                Log.e("유저 데이터 불러오기 실패", t.getMessage());
                t.printStackTrace();
            }
        });

    }
}