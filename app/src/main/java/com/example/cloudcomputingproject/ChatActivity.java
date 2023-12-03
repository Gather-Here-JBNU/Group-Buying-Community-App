package com.example.cloudcomputingproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.cloudcomputingproject.datas.CategoryDataResponse;
import com.example.cloudcomputingproject.datas.UserDataGet;
import com.example.cloudcomputingproject.datas.UserDataGetResponse;
import com.example.cloudcomputingproject.datas.UserDataInsert;
import com.example.cloudcomputingproject.datas.UserDataInsertResponse;
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
import java.util.Hashtable;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatActivity extends AppCompatActivity {
    private static final String TAG = "ChatActivity";
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    ArrayList<Chat> chatArrayList;
    MyAdapter mAdapter;
    EditText etText;
    ImageView ivSend;
    FirebaseDatabase database;

    APIInterface service;

    String stEmail; //삭제
    String u_id; // u_id
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        chatArrayList = new ArrayList<>();

        stEmail = getIntent().getStringExtra("email"); //삭제

        database = FirebaseDatabase.getInstance();

        TextView btnFinish = (TextView) findViewById(R.id.btnFinish);
        intent = getIntent();
        u_id = intent.getStringExtra("u_id"); // PostActivity에서, "채팅 확인용" 눌렀을 때 전달된 u_id 요소 가져오기.

        service = RetrofitClient.getClient().create(APIInterface.class); // 서버 연결


        startGet(new UserDataGet(u_id)); // u_id를 넣어주고, 서버와 통신할 것임.
        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ivSend = (ImageView) findViewById(R.id.btnSend);
        etText = (EditText)findViewById(R.id.etText);

        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new MyAdapter(chatArrayList, stEmail);
        recyclerView.setAdapter(mAdapter);

        ChildEventListener childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                Log.d(TAG, "onChildAdded:" + dataSnapshot.getKey());

                // A new comment has been added, add it to the displayed list
                Chat chat = dataSnapshot.getValue(Chat.class);
                String commentKey = dataSnapshot.getKey();
                String stEmail = chat.getEmail();
                String stText = chat.getText();
                Log.d(TAG, "stEmail: "+stEmail);
                Log.d(TAG, "stText: "+stText);
                chatArrayList.add(chat);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String previousChildName) {
                Log.d(TAG, "onChildChanged:" + dataSnapshot.getKey());

                // A comment has changed, use the key to determine if we are displaying this
                // comment and if so displayed the changed comment.

                // ...
            }
            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Log.d(TAG, "onChildRemoved:" + dataSnapshot.getKey());

                // A comment has changed, use the key to determine if we are displaying this
                // comment and if so remove it.
                String commentKey = dataSnapshot.getKey();

                // ...
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String previousChildName) {
                Log.d(TAG, "onChildMoved:" + dataSnapshot.getKey());

                // A comment has changed position, use the key to determine if we are
                // displaying this comment and if so move it.


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


        ivSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ChatDTO chat = new ChatDTO(user_name, etText.getText().toString()); //ChatDTO를 이용하여 데이터를 묶는다.
//                databaseReference.child("chat").child(CHAT_NAME).push().setValue(chat); //databaseReference를 이용해 데이터 푸쉬
//                chat_edit.setText(""); //입력창 초기화

                String stText = etText.getText().toString();
                //Toast.makeText(ChatActivity.this, "MSG : "+stText, Toast.LENGTH_LONG).show();

                Calendar c = Calendar.getInstance();
                SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM--dd hh:mm:ss");
                String datetime = dateformat.format(c.getTime());

                DatabaseReference myRef = database.getReference("message").child(datetime);

                Hashtable<String, String> numbers
                        = new Hashtable<String, String>();
                numbers.put("email", stEmail);
                numbers.put("text", stText);

                myRef.setValue(numbers);
                etText.getText().clear();
            }
        });
    }

    private void startGet(UserDataGet data){ // 입력된 데이터, u_id를바탕으로 통신할 것임.
        service.UserGet(data).enqueue(new Callback<UserDataGetResponse>() {
            @Override
            public void onResponse(Call<UserDataGetResponse> call, Response<UserDataGetResponse> response) {
                UserDataGetResponse result = response.body();
                // 성공시, result에 정보를 불러올 것임. 여기서 result에 대한 정보는 UserDataGetRespons.java에 명시되어 있음.

                if (result.getCode() == 200) {
                    Log.e("유저 데이터 불러오기 성공..", String.valueOf("."));
                    //
                    //result.getEmail(); 이메일 가져오기
                    //result.getInfo(); 자기소개 가져오기 -> 필요없으면 쓰지 않으면 됨.
                    //result.getNickname(); 닉네임 가져오기.

                    // 데이터 가져오기 성공했을 때, 이후에 다른 작업 진행하기.
                    // 예를들어, 채팅창에 가져온 email, nickname을 통해 채팅을 띄워주고 싶다면,
                    // 하나의 함수를 만들어서,
                    //  showText();
                    // 이런식으로 넣어주어야, 데이터를 먼저 불러오고 -> 이 데이터를 바탕으로 정보를 띄워줄 수 있음.
                    //finish();
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