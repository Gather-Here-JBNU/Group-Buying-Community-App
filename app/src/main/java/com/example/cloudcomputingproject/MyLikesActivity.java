package com.example.cloudcomputingproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cloudcomputingproject.datas.LikeDataResponse;
import com.example.cloudcomputingproject.datas.LikePostData;
import com.example.cloudcomputingproject.datas.LikePostDataReponse;
import com.example.cloudcomputingproject.datas.Post;
import com.example.cloudcomputingproject.datas.PostItem;
import com.example.cloudcomputingproject.datas.UserDataGet;
import com.example.cloudcomputingproject.datas.UserDataGetResponse;
import com.example.cloudcomputingproject.postlist.PostListAdapter;
import com.example.cloudcomputingproject.postlist.PostListPreview;
import com.example.cloudcomputingproject.postpage.PostAdapter;
import com.example.cloudcomputingproject.postpage.PostPreview;
import com.example.cloudcomputingproject.utility.APIInterface;
import com.example.cloudcomputingproject.utility.RetrofitClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyLikesActivity extends AppCompatActivity {
    Toolbar toolbar;

    private RecyclerView recyclerView;
    private PostListAdapter adapter;

    private List<PostListPreview> postPreviews;

    FirebaseFirestore db;

    String user_id;
    private APIInterface service;

    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_likes);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // 툴바 왼쪽에, 뒤로가기 버튼 추가.

        db = FirebaseFirestore.getInstance();

        user_id = FirebaseAuth.getInstance().getUid();

        service = RetrofitClient.getClient().create(APIInterface.class); // 서버 연결

        //recyclerView 초기화
        recyclerView = findViewById(R.id.likes_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        intent = getIntent();
        String other_user_id = intent.getStringExtra("user_id");
        if(other_user_id !=null){
            user_id = other_user_id; // 만약, 다른 사용자가 넘겨준 intent가 감지되었다면, 이 user_id로 접근할 것임.
            Log.d("테스트공간","");
        }

        String flag = "like";
        startGet(new LikePostData(user_id, flag));

    }


    private void startGet(LikePostData data){ // 입력된 데이터, u_id를바탕으로 통신할 것임.
        service.LikePostData(data).enqueue(new Callback<LikePostDataReponse>() {
            @Override
            public void onResponse(Call<LikePostDataReponse> call, Response<LikePostDataReponse> response) {
                LikePostDataReponse result = response.body();
                // 성공시, result에 정보를 불러올 것임. 여기서 result에 대한 정보는 UserDataGetRespons.java에 명시되어 있음.

                if (result.getCode() == 200) {
                    Log.e("유저 데이터 불러오기 성공..", String.valueOf("."));

                    List<PostItem> posts = result.getPosts();

                    for (int i = posts.size() - 1; i >= 0; i--) {
                        PostItem post = posts.get(i);
                        Log.d("", post.getPostId() + post.getNickname() + post.getTitle()) ;
                    }

                    showPost(posts);
                }
            }

            @Override
            public void onFailure(Call<LikePostDataReponse> call, Throwable t) {
                Log.e("유저 데이터 불러오기 실패", t.getMessage());
                t.printStackTrace();
            }
        });

    }


    public void showPost(List<PostItem> posts){

        postPreviews = new ArrayList<>();

        for (int i = posts.size() - 1; i >= 0; i--) {
            PostItem post= posts.get(i);
                postPreviews.add(new PostListPreview(post.getPostId(), post.getNickname(), post.getTitle(), post.getImg()));
                if(post.getImg()!=null){
                    Log.d("img는", post.getImg());
                }
                if(adapter == null){
                    adapter = new PostListAdapter(postPreviews);
                    recyclerView.setAdapter(adapter);
                }
                else {
                    adapter.notifyDataSetChanged();
                }
        }

        // 어댑터 생성 및 설정
        adapter = new PostListAdapter(postPreviews);
        recyclerView.setAdapter(adapter);

        // CustomItemDecoration을 추가합니다.
        //int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.item_decoration_margin);
        //recyclerView.addItemDecoration(new PostActivity.CustomItemDecoration(spacingInPixels));
    }
}