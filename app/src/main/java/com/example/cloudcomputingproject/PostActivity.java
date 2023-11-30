package com.example.cloudcomputingproject;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.content.Intent;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.cloudcomputingproject.postpage.PostAdapter;
import com.example.cloudcomputingproject.postpage.PostPreview;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class PostActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private PostAdapter adapter;
    private List<PostPreview> postPreviews;
    private FloatingActionButton postingButton;
    String u_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post); // 여러분의 레이아웃 파일 이름으로 변경하세요.
        Intent intent = getIntent();
        u_id = intent.getStringExtra("u_id"); // 로그인할때 전달해준 u_id를 변수에 저장.

        // 스피너 초기화
        Spinner spinner = findViewById(R.id.categorySelectBar); // XML 파일에 정의된 스피너 ID



        // 스피너에 들어갈 데이터 리스트 생성 (db의 카테고리 리스트에서 받아온 키로 생성할 예정)
        List<String> categories = new ArrayList<>();
        categories.add("운동 기구");
        categories.add("도서");
        categories.add("배달 음식");
        categories.add("채팅 확인용");
        categories.add("게시글 확인용");
        categories.add("마이페이지 확인용");
        categories.add("----카테고리 추가하기----");

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
                
                if(item.equals("----카테고리 추가하기----")){ // 카테고리 추가 선택 시 ,카테고리 추가 액티비티로 이동
                    Intent intent = new Intent(PostActivity.this, CategoryActivity.class);
                    startActivity(intent);
                } else if(item.equals("채팅 확인용")) { // 채팅 확인용. 누를시 채팅 액티비티 이동.
                    Intent intent = new Intent(PostActivity.this, ChatActivity.class);
                    startActivity(intent);
                } else if(item.equals("게시글 확인용")) { // 게시글 확인용. 누를시 게시글 액티비티 이동.
                    Intent intent = new Intent(PostActivity.this, ShowPostClickActivity.class);
                    startActivity(intent);
                } else if(item.equals("마이페이지 확인용")){
                    Intent intent = new Intent(PostActivity.this, Mypage.class);
                    startActivity(intent);
                }
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
        postPreviews.add(new PostPreview("이미지 url 넣는곳",
                "치킨 시켜 먹을사람 2명 구함", "참빛관 사는데 치킨 시켜먹을사람?"));
        postPreviews.add(new PostPreview("이미지 url 넣는곳",
                "피자 먹고싶은데 같이 배달시킬사람?", "사대부고 사는데 피자 배달비 반띵 할사람?"));
        postPreviews.add(new PostPreview("이미지 url 넣는곳",
                "예시입니다.", "리사이클러뷰 확인용."));
        postPreviews.add(new PostPreview("이미지 url 넣는곳",
                "예시입니다.", "리사이클러뷰 확인용."));
        postPreviews.add(new PostPreview("이미지 url 넣는곳",
                "예시입니다.", "리사이클러뷰 확인용."));
        postPreviews.add(new PostPreview("이미지 url 넣는곳",
                "예시입니다.", "리사이클러뷰 확인용."));
        postPreviews.add(new PostPreview("이미지 url 넣는곳",
                "예시입니다.", "리사이클러뷰 확인용."));
        postPreviews.add(new PostPreview("이미지 url 넣는곳",
                "예시입니다.", "리사이클러뷰 확인용."));
        postPreviews.add(new PostPreview("이미지 url 넣는곳",
                "예시입니다.", "리사이클러뷰 확인용."));
        postPreviews.add(new PostPreview("이미지 url 넣는곳",
                "예시입니다.", "리사이클러뷰 확인용."));

        // 어댑터 생성 및 설정
        adapter = new PostAdapter(postPreviews);
        recyclerView.setAdapter(adapter);

        // CustomItemDecoration을 추가합니다.
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.item_decoration_margin);
        recyclerView.addItemDecoration(new PostActivity.CustomItemDecoration(spacingInPixels));

        // FloatingActionButton 초기화
        postingButton = findViewById(R.id.postingButton);
        postingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 게시글 작성 페이지로 넘어가는 Intent 생성
                Intent intent = new Intent(PostActivity.this, AddPostActivity.class);
                startActivity(intent);
            }
        });
    }

    // CustomItemDecoration 클래스, 리사이클러뷰의 세부조정사항을 설정합니다.
    class CustomItemDecoration extends RecyclerView.ItemDecoration {
        private final int verticalSpaceHeight;

        public CustomItemDecoration(int verticalSpaceHeight) {
            this.verticalSpaceHeight = verticalSpaceHeight;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            outRect.bottom = verticalSpaceHeight;
            // 첫 번째 아이템에는 상단에도 공간을 추가합니다.
            if (parent.getChildAdapterPosition(view) == 0) {
                outRect.top = verticalSpaceHeight;
            }
        }
    }

}