package com.example.cloudcomputingproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

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

public class RegisterActivity extends AppCompatActivity {

    Button register_btn;
    LinearLayout login_move_layout;
    Toolbar toolbar;

    EditText email_et, pw_et, nickname_et;
    String email, pw, nickname;
    private FirebaseFirestore mStore;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        register_btn = findViewById(R.id.register_btn);
        login_move_layout = findViewById(R.id.login_move_layout);
        toolbar = findViewById(R.id.toolbar);

         mStore = FirebaseFirestore.getInstance(); // 파이어베이스 스토어
         mAuth = FirebaseAuth.getInstance(); // 파이어베이스 인스턴스 설정
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

                                        Map<String, Object> userMap = new HashMap<>();
                                        userMap.put(FirebaseID.user_id, user.getUid());
                                        userMap.put(FirebaseID.email, email);
                                        userMap.put(FirebaseID.password, pw);
                                        userMap.put(FirebaseID.nickname, nickname);

                                        //현재 유저의 Uid를 이름으로 한 document 생성. 이게 없으면 사용자 컨텐츠의 이륾과 사용자id이름이 달라 사용하기 힘듬
                                        mStore.collection(FirebaseID.user).document(user.getUid()).set(userMap, SetOptions.merge());

                                        //회원가입 성공시 로그인 액티비티로 화면 전환
                                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);

                                        startActivity(intent);
                                        finish();

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
}