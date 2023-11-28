package com.example.cloudcomputingproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    Button register_btn;
    LinearLayout login_move_layout;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        register_btn = findViewById(R.id.register_btn);
        login_move_layout = findViewById(R.id.login_move_layout);
        toolbar = findViewById(R.id.toolbar);

        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(RegisterActivity.this, "click", Toast.LENGTH_SHORT).show(); // 회원가입 버튼 눌렀을 때 동작. 잠시 toast로 대체
            }
        });

        login_move_layout.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(RegisterActivity.this, LoginActivty.class);
                // 회원가입 액티비티에서, 로그인 액티비티로 전환.
                startActivity(intent);
            }
        });

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false); // 타이틀 제거
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // 툴바 왼쪽, 뒤로가기 버튼 추가
    }
}