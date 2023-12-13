package com.example.cloudcomputingproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.cloudcomputingproject.datas.UserDataInsert;
import com.example.cloudcomputingproject.datas.UserDataInsertResponse;
import com.example.cloudcomputingproject.utility.APIInterface;
import com.example.cloudcomputingproject.utility.RetrofitClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.HashMap;
import java.util.Map;

import com.example.cloudcomputingproject.utility.FirebaseID;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    Button register_btn;
    LinearLayout login_move_layout;
    Toolbar toolbar;

    EditText email_et, pw_et, nickname_et;
    String email, pw, nickname, u_id;
    private FirebaseFirestore mStore;
    private FirebaseAuth mAuth;
    private APIInterface service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        register_btn = findViewById(R.id.register_btn);
        login_move_layout = findViewById(R.id.login_move_layout);
        toolbar = findViewById(R.id.toolbar);

         mStore = FirebaseFirestore.getInstance(); // 파이어베이스 스토어
         mAuth = FirebaseAuth.getInstance(); // 파이어베이스 인스턴스 설정

        service = RetrofitClient.getClient().create(APIInterface.class); // 서버 연결
        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toast.makeText(RegisterActivity.this, "click", Toast.LENGTH_SHORT).show(); // 회원가입 버튼 눌렀을 때 동작. 잠시 toast로 대체

                email_et = findViewById(R.id.email_et);
                pw_et = findViewById(R.id.pw_et);
                nickname_et = findViewById(R.id.nickname_et);

                email = email_et.getText().toString();
                pw = pw_et.getText().toString();
                nickname = nickname_et.getText().toString();

                if ((email != null) && !email.isEmpty() && (pw != null) && !pw.isEmpty() && (nickname != null) && !nickname.isEmpty()) {
                    mAuth.createUserWithEmailAndPassword(email, pw)
                            .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {

                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        u_id = user.getUid();

                                        // 파이어베이스 계정 생성 후, 서버 RDS 데이터베이스에 삽입
                                        startInsert(new UserDataInsert(u_id, email, pw, nickname));

                                       //Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                        //startActivity(intent);
                                        Log.e("파이어베이스 이후", String.valueOf("12"));
                                        // finish();

                                    } else {
                                        Exception exception = task.getException();
                                        if (exception != null) {
                                            Toast.makeText(RegisterActivity.this, "회원가입 실패: " + exception.getMessage(),
                                                    Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(RegisterActivity.this, "회원가입 실패", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }
                            });
                }
            }
    });

        login_move_layout.setOnClickListener(new View.OnClickListener(){
        public void onClick(View v){
            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            // 회원가입 액티비티에서, 로그인 액티비티로 전환.
            startActivity(intent);
        }
    });

    setSupportActionBar(toolbar);
    getSupportActionBar().setDisplayShowTitleEnabled(false); // 타이틀 제거
    getSupportActionBar().setDisplayHomeAsUpEnabled(true); // 툴바 왼쪽, 뒤로가기 버튼 추가
}

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        //updateUI(currentUser);
    }
    
    private void startInsert(UserDataInsert data){
        service.userLoginInsert(data).enqueue(new Callback<UserDataInsertResponse>() {
            @Override
            public void onResponse(Call<UserDataInsertResponse> call, Response<UserDataInsertResponse> response) {
                UserDataInsertResponse result = response.body();

                if (result.getCode() == 200) {
                    Log.e("회원가입 성공. mysql 삽입", String.valueOf("12"));
                    //finish();
                }
            }

            @Override
            public void onFailure(Call<UserDataInsertResponse> call, Throwable t) {
                Toast.makeText(RegisterActivity.this, "회원가입 에러 발생", Toast.LENGTH_SHORT).show();
                Log.e("회원가입 에러 발생", t.getMessage());
                t.printStackTrace();
            }
        });
    }
}