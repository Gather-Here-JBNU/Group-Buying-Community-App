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

        // CategoryActivity or PostActivity를 시작하는 Intent를 생성합니다.
        Intent intent = new Intent(this, PostActivity.class);
        startActivity(intent);

    }
}