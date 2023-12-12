package com.example.cloudcomputingproject;

import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.cloudcomputingproject.categorypage.CategoryAdapter;
import com.example.cloudcomputingproject.categorypage.CategoryItem;
import com.example.cloudcomputingproject.datas.CategoryDataResponse;
import com.example.cloudcomputingproject.datas.CategoryData;
import com.example.cloudcomputingproject.utility.APIInterface;
import com.example.cloudcomputingproject.utility.RetrofitClient;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.AppCompatActivity;

public class CategoryActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private CategoryAdapter adapter;
    private List<CategoryItem> categoryList;
    private APIInterface service;
    private EditText categoryEditText;
    private ImageButton categoryAddButton;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.category);


        categoryEditText = findViewById(R.id.categoryEditText);
        categoryAddButton = findViewById(R.id.categoryAddButton);
        recyclerView = findViewById(R.id.categoryView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        categoryList = new ArrayList<>();
        adapter = new CategoryAdapter(categoryList);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new CustomItemDecoration(getResources().getDimensionPixelSize(R.dimen.item_decoration_margin)));
        service = RetrofitClient.getClient().create(APIInterface.class);

        // Category라는 문자열이 xml코드의 수정을 통해서도 툴바에 안나타나길래 넣은 부분입니다.
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(R.string.category_title);
        }




        // 카테고리 데이터를 서버로부터 가져오는 메서드 호출
        getCategoryFromServer();

        categoryAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // EditText에서 카테고리 이름을 가져옵니다.
                String categoryName = categoryEditText.getText().toString().trim();
                if (!categoryName.isEmpty()) {
                    // 서버에 전송할 카테고리 데이터 객체를 생성합니다.
                    CategoryData newCategory = new CategoryData(categoryName);

                    // Retrofit 서비스를 사용하여 서버에 카테고리를 추가하는 요청을 보냅니다.
                    service.addCategory(newCategory).enqueue(new Callback<CategoryDataResponse>() {
                        @Override
                        public void onResponse(Call<CategoryDataResponse> call, Response<CategoryDataResponse> response) {
                            if (response.isSuccessful() && response.body() != null) {
                                CategoryDataResponse res = response.body();
                                if (res.getCode() == 200) {
                                    // 서버로부터 성공 응답을 받았을 때
                                    Toast.makeText(CategoryActivity.this, "카테고리가 추가되었습니다.", Toast.LENGTH_SHORT).show();

                                    // 카테고리 리스트를 업데이트하고 UI를 새로 고칩니다.
                                    categoryList.add(new CategoryItem(categoryName));
                                    adapter.notifyDataSetChanged();

                                    // EditText를 비웁니다.
                                    categoryEditText.setText("");
                                } else {
                                    // 서버로부터 실패 응답을 받았을 때
                                    Toast.makeText(CategoryActivity.this, "카테고리 추가 실패: " + res.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                // 서버 응답에 문제가 있는 경우
                                Toast.makeText(CategoryActivity.this, "응답 실패: " + response.message(), Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<CategoryDataResponse> call, Throwable t) {
                            // 네트워크 오류 처리
                            Toast.makeText(CategoryActivity.this, "네트워크 오류가 발생했습니다: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    // EditText가 비어있는 경우 사용자에게 알립니다.
                    Toast.makeText(CategoryActivity.this, "카테고리 이름을 입력해주세요.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void getCategoryFromServer() {
        service.CategoryGet().enqueue(new Callback<CategoryDataResponse>() {
            @Override
            public void onResponse(Call<CategoryDataResponse> call, Response<CategoryDataResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    CategoryDataResponse result = response.body();
                    if (result.getCode() == 200) {
                        updateCategoryList(result.getCategoryLabel());
                    }
                } else {
                    Toast.makeText(CategoryActivity.this, "카테고리를 불러오는데 실패했습니다.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CategoryDataResponse> call, Throwable t) {
                Toast.makeText(CategoryActivity.this, "네트워크 오류가 발생했습니다.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateCategoryList(List<String> categoryLabels) {
        categoryList.clear();
        for (String label : categoryLabels) {
            categoryList.add(new CategoryItem(label));
        }
        adapter.notifyDataSetChanged();
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