package com.example.cloudcomputingproject;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.cloudcomputingproject.postpage.PostAdapter;
import com.example.cloudcomputingproject.postpage.PostPreview;


public class PostActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private PostAdapter adapter;
    private List<PostPreview> postPreviews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post); // 여러분의 레이아웃 파일 이름으로 변경하세요.

        // 스피너 초기화
        Spinner spinner = findViewById(R.id.categorySelectBar); // XML 파일에 정의된 스피너 ID

        // 스피너에 들어갈 데이터 리스트 생성 (db의 카테고리 리스트에서 받아온 키로 생성할 예정)
        List<String> categories = new ArrayList<>();
        categories.add("운동 기구");
        categories.add("도서");
        categories.add("배달 음식");


        // 어댑터 생성 및 설정
        ArrayAdapter<String> postpageCategorySpinnerAdapter = new ArrayAdapter<>(this, R.layout.postpage_category_spinner_item, categories); // 스피너를 클릭하지 않았을 때의 기본 레이아웃
        postpageCategorySpinnerAdapter.setDropDownViewResource(R.layout.postpage_category_spinner_listview); //스피너를 클릭해서 드롭다운 했을 때의 레이아웃
        spinner.setAdapter(postpageCategorySpinnerAdapter);

        // categorySelectbox스피너 아이템 선택 이벤트 리스너 설정
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // 아이템 선택시 동작
                String item = parent.getItemAtPosition(position).toString();
                // 선택된 아이템을 사용한 추가 동작 구현
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // 아무것도 선택되지 않았을 때의 동작
            }
        });

        // RecyclerView 초기화
        recyclerView = findViewById(R.id.postView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // 임시 데이터 생성
        postPreviews = new ArrayList<>();
        // 여기에 임의의 데이터 추가...
        postPreviews.add(new PostPreview("http://www.bhc.co.kr/upload/bhc/menu/%EB%BF%8C%EB%A7%81%ED%81%B4_233x160.png",
                "치킨 시켜 먹을사람", "참빛관 사는데 치킨 시켜먹을사람?"));

        // 어댑터 생성 및 설정
        adapter = new PostAdapter(postPreviews);
        recyclerView.setAdapter(adapter);
    }
}