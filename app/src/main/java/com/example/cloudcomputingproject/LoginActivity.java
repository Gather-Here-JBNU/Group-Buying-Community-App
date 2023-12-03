package com.example.cloudcomputingproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
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

public class LoginActivity extends AppCompatActivity {

    Button login_btn;
    LinearLayout register_move_layout;
    Toolbar toolbar;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private SharedPreferences appData;
    private String id;
    private boolean saveLoginData;
    EditText email_et, pw_et;
    String email, pw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_activty);

        login_btn = findViewById(R.id.login_btn);
        register_move_layout = findViewById(R.id.register_move_layout);
        toolbar = findViewById(R.id.toolbar);


        appData = getSharedPreferences("appData", MODE_PRIVATE);
        load();

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email_et = findViewById(R.id.email_et);
                pw_et = findViewById(R.id.pw_et);

                email = email_et.getText().toString();
                pw = pw_et.getText().toString();

                mAuth.signInWithEmailAndPassword(email, pw)
                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {//성공했을때
                                    Intent intent = new Intent(LoginActivity.this, PostActivity.class);
                                    FirebaseUser cur_user = mAuth.getCurrentUser();
                                    String u_id = cur_user.getUid();
                                    Log.e("LoginActivity에서 u_id는 ", u_id);
                                    intent.putExtra("u_id", u_id); // 파이어베이스 아이디를 intent에 전송. 이를 통해 aws db에 접근할 것임.
                                    intent.putExtra("email", email);

                                    startActivity(intent);
                                } else {//실패했을때
                                    Toast.makeText(LoginActivity.this, "로그인 오류", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                save();
            }
        });

        register_move_layout.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                // 로그인 액티비티에서, 회원가입 액티비티로 전환.
                startActivity(intent);
            }
        });

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false); // 타이틀 제거
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // 툴바 왼쪽, 뒤로가기 버튼 추가
    }

    private void save() {
        // SharedPreferences 객체만으론 저장 불가능 Editor 사용
        SharedPreferences.Editor editor = appData.edit();

        // 에디터객체.put타입( 저장시킬 이름, 저장시킬 값 )
        // 저장시킬 이름이 이미 존재하면 덮어씌움
        editor.putBoolean("SAVE_LOGIN_DATA", false);
        editor.putString("ID", email);

        // apply, commit 을 안하면 변경된 내용이 저장되지 않음
        editor.apply();
    }

    // 설정값을 불러오는 함수
    private void load() {
        // SharedPreferences 객체.get타입( 저장된 이름, 기본값 )
        // 저장된 이름이 존재하지 않을 시 기본값
        saveLoginData = appData.getBoolean("SAVE_LOGIN_DATA", false);
        id = appData.getString("ID", "");
    }
}