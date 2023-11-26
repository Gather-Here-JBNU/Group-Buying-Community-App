package com.example.cloudcomputingproject;

import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.cloudcomputingproject.categorypage.CategoryAdapter;
import com.example.cloudcomputingproject.categorypage.CategoryItem;
import java.util.ArrayList;
import java.util.List;

public class CategoryActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private CategoryAdapter adapter;
    private List<CategoryItem> categoryList;

    @Override
    protected void onCreate(Bundle savedInstanceState) { //여기에서 db의 카테고리 종류를 가져와서 생성하면 됩니다.
        super.onCreate(savedInstanceState);
        setContentView(R.layout.category);

        recyclerView = findViewById(R.id.category_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        categoryList = new ArrayList<>();
        categoryList.add(new CategoryItem("음식배달"));
        categoryList.add(new CategoryItem("도서"));
        categoryList.add(new CategoryItem("운동기구"));


        adapter = new CategoryAdapter(categoryList);
        recyclerView.setAdapter(adapter);

        // CustomItemDecoration을 추가합니다.
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.item_decoration_margin);
        recyclerView.addItemDecoration(new CustomItemDecoration(spacingInPixels));
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
