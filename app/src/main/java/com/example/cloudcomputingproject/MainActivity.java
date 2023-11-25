package com.example.cloudcomputingproject;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        //setContentView(R.layout.post_preview_template);
        // CategoryActivity를 시작하는 Intent를 생성합니다.
        Intent intent = new Intent(this, CategoryActivity.class);
        startActivity(intent);

        // MainActivity를 종료하여, 뒤로 가기 버튼을 눌렀을 때 MainActivity로 돌아가지 않게 합니다.
        finish();
    }
}