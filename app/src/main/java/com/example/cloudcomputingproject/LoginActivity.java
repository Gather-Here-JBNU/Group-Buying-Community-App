package com.example.cloudcomputingproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    Button login_btn;
    LinearLayout register_move_layout;
    Toolbar toolbar;
    private final FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private SharedPreferences appData;
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

        pw_et = findViewById(R.id.pw_et);
        // 비밀번호 누르고 엔터를 누르면 로그인 버튼 클릭
        pw_et.setOnEditorActionListener((v, actionId, event) -> {
            if(actionId == EditorInfo.IME_ACTION_DONE || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN)){
                login_btn.performClick();
                return true;
            }
            return false;
        });


        login_btn.setOnClickListener(v -> {
            email_et = findViewById(R.id.email_et);

            email = email_et.getText().toString();
            pw = pw_et.getText().toString();


            mAuth.signInWithEmailAndPassword(email, pw)
                    .addOnCompleteListener(LoginActivity.this, task -> {
                        if (task.isSuccessful()) {//성공했을때
                            Intent intent = new Intent(LoginActivity.this, PostActivity.class);
                            FirebaseUser cur_user = mAuth.getCurrentUser();
                            String u_id = cur_user.getUid();
                            Log.e("LoginActivity에서 u_id는 ", u_id);
                            intent.putExtra("email", email);

                            startActivity(intent);
                        } else {//실패했을때
                            Toast.makeText(LoginActivity.this, "로그인 오류", Toast.LENGTH_SHORT).show();
                        }
                    });
            save();
        });

        register_move_layout.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            // 로그인 액티비티에서, 회원가입 액티비티로 전환.
            startActivity(intent);
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
    }
}