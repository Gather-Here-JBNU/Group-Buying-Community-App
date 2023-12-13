package com.example.cloudcomputingproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.cloudcomputingproject.datas.UserDataGet;
import com.example.cloudcomputingproject.datas.UserDataGetResponse;
import com.example.cloudcomputingproject.datas.UserDataUpdate;
import com.example.cloudcomputingproject.datas.UserDataUpdateResponse;
import com.example.cloudcomputingproject.utility.APIInterface;
import com.example.cloudcomputingproject.utility.RetrofitClient;
import com.google.firebase.auth.FirebaseAuth;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileChange extends AppCompatActivity {
    APIInterface service;
    Intent intent;
    String u_id, email, nickname, info;
    TextView tvEmail;
    EditText etNickname, etInfo;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_change);

        service = RetrofitClient.getClient().create(APIInterface.class); // 서버 연결

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // 툴바 왼쪽에, 뒤로가기 버튼 추가.

        // Intent로부터 u_id 가져오기
        intent = getIntent();
        u_id = FirebaseAuth.getInstance().getUid();

        // UI 구성 요소
        tvEmail = findViewById(R.id.email_tv);
        etNickname = findViewById(R.id.nickname_et);
        etInfo = findViewById(R.id.selfintro_et);

        // u_id를 넣어주고, 서버와 통신할 것임.
        startGet(new UserDataGet(u_id));
    }

    /**
     * 툴바 오른쪽에 '완료' 버튼 추가.
     * @param menu The options menu in which you place your items.
     * @return true
     */
    public boolean onCreateOptionsMenu(Menu menu) { // 액티비티가 생성될 때 자동으로 호출.
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.save_btn_text, menu); // 완료 버튼 추가

        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){ // 액션바 항목 선택시 실행되는 메소드.
        if(item.getItemId() == R.id.toolbar_save_btn_text){ // 완료 버튼 눌렀을 시 동작. 잠시 toast로 대체.
            Toast.makeText(this, "저장되었습니다.", Toast.LENGTH_SHORT).show();
            nickname = etNickname.getText().toString();
            info = etInfo.getText().toString();
            Log.d("ProfileChange", "nickname저장하기 :      "+nickname);
            Log.d("ProfileChange", "info저장하기 :      "+info);
            startUpdate(new UserDataUpdate(u_id, nickname, info));
        }
        return super.onOptionsItemSelected(item);
    }

    private void startGet(UserDataGet data){ // 입력된 데이터, u_id를바탕으로 통신할 것임.
        service.UserGet(data).enqueue(new Callback<UserDataGetResponse>() {
            @Override
            public void onResponse(Call<UserDataGetResponse> call, Response<UserDataGetResponse> response) {
                UserDataGetResponse result = response.body();
                // 성공시, result에 정보를 불러올 것임. 여기서 result에 대한 정보는 UserDataGetRespons.java에 명시되어 있음.

                if (result.getCode() == 200) {
                    Log.e("유저 데이터 불러오기 성공..", String.valueOf("."));

                    email = result.getEmail();       // 이메일 가져오기
                    nickname = result.getNickname(); // 닉네임 가져오기.
                    info = result.getInfo();       // 자기소개 가져오기

                    tvEmail.setText(email);
                    etNickname.setText(nickname);
                    etInfo.setText(info);

                    // 데이터 가져오기 성공했을 때, 이후에 다른 작업 진행하기.
                    // 예를들어, 채팅창에 가져온 email, nickname을 통해 채팅을 띄워주고 싶다면,
                    // 하나의 함수를 만들어서,
                    // showText();
                    // 이런식으로 넣어주어야, 데이터를 먼저 불러오고 -> 이 데이터를 바탕으로 정보를 띄워줄 수 있음.
                }
            }

            @Override
            public void onFailure(Call<UserDataGetResponse> call, Throwable t) {
                Toast.makeText(ProfileChange.this, "회원가입 에러 발생", Toast.LENGTH_SHORT).show();
                Log.e("유저 데이터 불러오기 실패", t.getMessage());
                t.printStackTrace();
            }
        });

    }


    // 입력된 데이터와 u_id를 기반으로 서버와 통신하는 메서드
    private void startUpdate(UserDataUpdate data){ // 입력된 데이터, u_id를바탕으로 통신할 것임.
        service.UserUpdate(data).enqueue(new Callback<UserDataUpdateResponse>() {
            @Override
            public void onResponse(Call<UserDataUpdateResponse> call, Response<UserDataUpdateResponse> response) {
                UserDataUpdateResponse result = response.body();
                // 성공시, result에 정보를 불러올 것임. 여기서 result에 대한 정보는 UserDataGetRespons.java에 명시되어 있음.

                if (result.getCode() == 200) {
                    Log.e("유저 데이터 불러오기 성공..", String.valueOf("."));
                    Log.d("ProfileChange", "nickname 업데이트 " + result.getNickname());
                    Log.d("ProfileChange", "info 업데이트 " + result.getInfo());
                    //tvEmail.setText(result.getEmail());       // 이메일 가져오기
                    //etNickname.setText(result.getNickname()); // 닉네임 가져오기.
                    //etInfo.setText(result.getInfo());       // 자기소개 가져오기 -> 필요없으면 쓰지 않으면 됨.

                    // 데이터 가져오기 성공했을 때, 이후에 다른 작업 진행하기.
                    // 예를들어, 채팅창에 가져온 email, nickname을 통해 채팅을 띄워주고 싶다면,
                    // 하나의 함수를 만들어서,
                    // showText();
                    // 이런식으로 넣어주어야, 데이터를 먼저 불러오고 -> 이 데이터를 바탕으로 정보를 띄워줄 수 있음.
                }
            }

            @Override
            public void onFailure(Call<UserDataUpdateResponse> call, Throwable t) {
                Toast.makeText(ProfileChange.this, "회원가입 에러 발생", Toast.LENGTH_SHORT).show();
                Log.e("유저 데이터 불러오기 실패", t.getMessage());
                t.printStackTrace();
            }
        });

    }
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(ProfileChange.this, Mypage.class);
        startActivity(intent);
        finish();
    }
}