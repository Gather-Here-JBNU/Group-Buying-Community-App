package com.example.cloudcomputingproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.cloudcomputingproject.datas.PutPostData;
import com.example.cloudcomputingproject.datas.PutPostDataResponse;
import com.example.cloudcomputingproject.datas.UserDataInsert;
import com.example.cloudcomputingproject.datas.UserDataInsertResponse;
import com.example.cloudcomputingproject.utility.APIInterface;
import com.example.cloudcomputingproject.utility.RetrofitClient;
import com.google.firebase.firestore.auth.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddPostActivity extends AppCompatActivity {
    Toolbar toolbar;
    private APIInterface service;

    EditText title_et, contents_et, img_et, price_et, location_et;
    String u_id, title, contents, img, category_label, price, location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addpost);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // 툴바 왼쪽에, 뒤로가기 버튼 추가.

        service = RetrofitClient.getClient().create(APIInterface.class); // 서버 연결
        Intent intent = getIntent();

        u_id = intent.getStringExtra("u_id"); // 로그인할때 전달해준 u_id를 변수에 저장.
        category_label = intent.getStringExtra("category"); // PostActivity에서 선택한 category 전달.
    }
    /**
     * 툴바 오른쪽에 '저장' 버튼 추가.
     * @param menu The options menu in which you place your items.
     * @return true
     */
    public boolean onCreateOptionsMenu(Menu menu) { // 액티비티가 생성될 때 자동으로 호출.
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.save_btn_text, menu); // 저장 버튼 추가
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item){ // 액션바 항목 선택시 실행되는 메소드.
        if(item.getItemId() == R.id.toolbar_save_btn_text){ // 저장 버튼 눌렀을 시 동작.
            title_et = findViewById(R.id.title_et);
            contents_et = findViewById(R.id.contents_et);
            price_et = findViewById(R.id.price_et);
            location_et = findViewById(R.id.location_et);

            title = title_et.getText().toString();
            contents = contents_et.getText().toString();
            price = price_et.getText().toString();
            location = location_et.getText().toString();

            img = "temp";

            startInsert(new PutPostData(u_id, title, contents, img, category_label, price, location));
        }
        return super.onOptionsItemSelected(item);
    }
    private void startInsert(PutPostData data) {
        service.PostInsert(data).enqueue(new Callback<PutPostDataResponse>() {
            @Override
            public void onResponse(Call<PutPostDataResponse> call, Response<PutPostDataResponse> response) {
                PutPostDataResponse result = response.body();
                data.PutPostDataPrint();
                Log.e("u_id : ", String.valueOf(u_id));
                Log.e("category :", String.valueOf(category_label));
                if(response == null){
                    Log.e("", String.valueOf("response는 null"));
                }
                if(result == null){
                    Log.e("", String.valueOf("result는 null"));
                }
                Toast.makeText(AddPostActivity.this, "onResponse 안입니다.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<PutPostDataResponse> call, Throwable t) {
                Toast.makeText(AddPostActivity.this, "게시글 작성", Toast.LENGTH_SHORT).show();
                Log.e("게시글 작성 에러 발생", t.getMessage());
                t.printStackTrace();
            }
        });
    }
}