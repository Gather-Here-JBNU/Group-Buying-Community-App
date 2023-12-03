package com.example.cloudcomputingproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.cloudcomputingproject.datas.UserDataGet;
import com.example.cloudcomputingproject.datas.UserDataGetResponse;
import com.example.cloudcomputingproject.utility.APIInterface;
import com.example.cloudcomputingproject.utility.RetrofitClient;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatActivity extends AppCompatActivity {
    private static final String TAG = "ChatActivity";
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    ArrayList<Chat> chatArrayList;
    MyAdapter mAdapter;
    FirebaseDatabase database;
    APIInterface service;
    String u_id, email, nickname, info; // u_id
    Intent intent;
    String time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        // 변수 초기화
        chatArrayList = new ArrayList<>();
        database = FirebaseDatabase.getInstance();
        service = RetrofitClient.getClient().create(APIInterface.class); // 서버 연결

        // Intent로부터 u_id 가져오기
        intent = getIntent();
        u_id = intent.getStringExtra("u_id"); // PostActivity에서, "채팅 확인용" 눌렀을 때 전달된 u_id 요소 가져오기.

        // UI 구성 요소
        TextView btnFinish = findViewById(R.id.btnFinish);
        ImageView ivSend = findViewById(R.id.btnSend);
        EditText etText = findViewById(R.id.etText);

        // u_id를 넣어주고, 서버와 통신할 것임.
        startGet(new UserDataGet(u_id));

        // 채팅 메시지를 위한 RecyclerView 설정
        recyclerView = findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new MyAdapter(chatArrayList, email);
        recyclerView.setAdapter(mAdapter);


        //나가기 버튼 활성화
        btnFinish.setOnClickListener(v -> finish());

        // Firebase Realtime Database의 ChildEventListener 설정
        ChildEventListener childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                Log.d(TAG, "onChildAdded:" + dataSnapshot.getKey());

                // A new comment has been added, add it to the displayed list
                Chat chat = dataSnapshot.getValue(Chat.class);
                String commentKey = dataSnapshot.getKey();
                String stEmail = chat.getEmail();
                String stText = chat.getText();
                String stNickname = chat.getNickname();
                Log.d(TAG, "stEmail: "+stEmail);
                Log.d(TAG, "stText: "+stText);
                Log.d(TAG, "stNickname: "+stNickname);
                Log.d(TAG, "stTime: "+time);
                chatArrayList.add(chat);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String previousChildName) {
                Log.d(TAG, "onChildChanged:" + dataSnapshot.getKey());

                // 댓글이 변경되었을 때, 해당 댓글을 표시하는지 확인

                // ...
            }
            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Log.d(TAG, "onChildRemoved:" + dataSnapshot.getKey());

                // 댓글이 삭제되었을 때, 해당 댓글을 표시하는지 확인

                // ...
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String previousChildName) {
                Log.d(TAG, "onChildMoved:" + dataSnapshot.getKey());

                // 댓글의 위치가 변경되었을 때, 해당 댓글을 표시하는지 확인

                // ...
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "postComments:onCancelled", databaseError.toException());
                Toast.makeText(ChatActivity.this, "Failed to load comments.",
                        Toast.LENGTH_SHORT).show();
            }
        };

        DatabaseReference ref = database.getReference("message");
        ref.addChildEventListener(childEventListener);

        // 메시지 전송을 위한 클릭 리스너
        ivSend.setOnClickListener(v -> {
            String stText = etText.getText().toString();
            String stEmail = email;
            String stNickname = nickname;
            Log.d(TAG,"텍스트 입력이 되었습니다." + stText);

            Calendar c = Calendar.getInstance();
            SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            String datetime = dateformat.format(c.getTime());

            SimpleDateFormat timeformat = new SimpleDateFormat("HH:mm");
            time = timeformat.format(c.getTime());

            Chat chat = new Chat(stEmail, stNickname, stText, time);

            Log.d(TAG,"email은 \"" + stEmail + "\" text는 \"" + stText + "\" time은 \"" + time + "\" 입니다.");

            // Firebase Realtime Database에 데이터 전송
            DatabaseReference myRef = database.getReference("message").child(datetime);
            myRef.setValue(chat);
            etText.getText().clear();
        });
    }

    // 입력된 데이터와 u_id를 기반으로 서버와 통신하는 메서드
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
                    //info = result.getInfo();       // 자기소개 가져오기 -> 필요없으면 쓰지 않으면 됨.

                    // 데이터 가져오기 성공했을 때, 이후에 다른 작업 진행하기.
                    // 예를들어, 채팅창에 가져온 email, nickname을 통해 채팅을 띄워주고 싶다면,
                    // 하나의 함수를 만들어서,
                    // showText();
                    // 이런식으로 넣어주어야, 데이터를 먼저 불러오고 -> 이 데이터를 바탕으로 정보를 띄워줄 수 있음.
                }
            }

            @Override
            public void onFailure(Call<UserDataGetResponse> call, Throwable t) {
                Toast.makeText(ChatActivity.this, "회원가입 에러 발생", Toast.LENGTH_SHORT).show();
                Log.e("유저 데이터 불러오기 실패", t.getMessage());
                t.printStackTrace();
            }
        });

    }
}