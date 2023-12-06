package com.example.cloudcomputingproject;

import static com.google.common.io.Files.getFileExtension;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cloudcomputingproject.datas.PostDataPut;
import com.example.cloudcomputingproject.datas.PostDataPutResponse;
import com.example.cloudcomputingproject.utility.APIInterface;
import com.example.cloudcomputingproject.utility.RetrofitClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddPostActivity extends AppCompatActivity {
    Toolbar toolbar;
    TextView toolbarTitle;
    private APIInterface service;

    EditText title_et, contents_et, img_et, price_et, location_et;
    String u_id, title, contents, img, category_label, price, location;
    //FrameLayout img_fr;
    ImageView img_iv;
    ProgressBar progressBar;

    private Uri imageUri;

    private final StorageReference reference = FirebaseStorage.getInstance().getReference();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addpost);


        img_iv = findViewById(R.id.img_iv);
        progressBar = findViewById(R.id.progress_view);

        toolbar = findViewById(R.id.toolbar);
        toolbarTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // 툴바 왼쪽에, 뒤로가기 버튼 추가.

        service = RetrofitClient.getClient().create(APIInterface.class); // 서버 연결
        Intent intent = getIntent();

        u_id = intent.getStringExtra("u_id"); // 로그인할때 전달해준 u_id를 변수에 저장.
        category_label = intent.getStringExtra("category"); // PostActivity에서 선택한 category 전달.

        //툴바의 textView에 category_label 설정
        toolbarTitle.setText(category_label);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        // 초기에는 프로그래스바 감추기
        progressBar.setVisibility(View.INVISIBLE);

        img_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/");
                activityResult.launch(intent);
            }
        });


    } // onCreate

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

            if(imageUri != null){
                uploadToFirebase(imageUri);
            }
        }
        return super.onOptionsItemSelected(item);
    }

    // 사진 가져오기..
    ActivityResultLauncher<Intent> activityResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>(){
                public void onActivityResult(ActivityResult result){
                    if(result.getResultCode() == RESULT_OK && result.getData() != null){
                        imageUri = result.getData().getData();
                        Log.d("imageUri는 :", imageUri.toString());


                        // ImageView의 레이아웃 파라미터를 가져옵니다.
                        ViewGroup.LayoutParams params = img_iv.getLayoutParams();


                        // 가로와 세로를 MATCH_PARENT로 설정합니다.
                        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
                        params.height = ViewGroup.LayoutParams.MATCH_PARENT;

                        // 변경된 레이아웃 파라미터를 ImageView에 적용합니다.
                        img_iv.setLayoutParams(params);

                        img_iv.setImageURI(imageUri);
                    }
                }
            }
    );

    //파이어베이스 이미지 업로드
    private void uploadToFirebase(Uri uri) {

        StorageReference fileRef = reference.child(System.currentTimeMillis() + "." + getFileExtension(uri.toString()));

        fileRef.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        // 나중에 접근 가능한 uri를 db에 넣자!
                        Log.d("이미지 파일 업로드 성공 !!", uri.toString());

                        img = uri.toString();
                        Log.d("img 변수는" , img);

                        // uri를 img에 string으로 저장
                        startInsert(new PostDataPut(u_id, title, contents, uri.toString(), category_label, price, location));
                        // 이후, uri를 포함하여 db에 insert
                        //프로그래스바 숨김
                        progressBar.setVisibility(View.INVISIBLE);

                        Toast.makeText(AddPostActivity.this, "업로드 성공", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                //프로그래스바 보여주기
                progressBar.setVisibility(View.VISIBLE);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //프로그래스바 숨김
                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(AddPostActivity.this, "업로드 실패", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void startInsert(PostDataPut data) {
        service.PostInsert(data).enqueue(new Callback<PostDataPutResponse>() {
            @Override
            public void onResponse(Call<PostDataPutResponse> call, Response<PostDataPutResponse> response) {
                PostDataPutResponse result = response.body();
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
            public void onFailure(Call<PostDataPutResponse> call, Throwable t) {
                Toast.makeText(AddPostActivity.this, "게시글 작성", Toast.LENGTH_SHORT).show();
                Log.e("게시글 작성 에러 발생", t.getMessage());
                t.printStackTrace();
            }
        });
    }

}