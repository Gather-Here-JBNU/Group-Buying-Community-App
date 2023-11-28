package com.example.cloudcomputingproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

public class LoginActivty extends AppCompatActivity {

    Button login_btn;
    LinearLayout register_move_layout;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_activty);

        login_btn = findViewById(R.id.login_btn);
        register_move_layout = findViewById(R.id.register_move_layout);
        toolbar = findViewById(R.id.toolbar);

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivty.this, PostActivity.class);
                startActivity(intent);
                //Toast.makeText(LoginActivty.this, "click", Toast.LENGTH_SHORT).show(); // 로그인 버튼 눌렀을 때 동작. 잠시 toast로 대체
            }
        });

        register_move_layout.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(LoginActivty.this, RegisterActivity.class);
                // 로그인 액티비티에서, 회원가입 액티비티로 전환.
                startActivity(intent);
            }
        });

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false); // 타이틀 제거
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // 툴바 왼쪽, 뒤로가기 버튼 추가
    }
}