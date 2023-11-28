package com.example.cloudcomputingproject.categorypage;

import com.example.cloudcomputingproject.R;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {
    private List<CategoryItem> categoryList;

    public CategoryAdapter(List<CategoryItem> categoryList) {
        this.categoryList = categoryList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item_template, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        CategoryItem item = categoryList.get(position);
        holder.categoryName.setText(item.getName());

        // favoriteButton 클릭 리스너 설정
        holder.favoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 현재 버튼의 이미지를 확인하고 변경합니다.
                if ((int) holder.favoriteButton.getTag() == android.R.drawable.btn_star_big_on) {
                    holder.favoriteButton.setImageResource(android.R.drawable.btn_star_big_off);
                    holder.favoriteButton.setTag(android.R.drawable.btn_star_big_off);
                } else {
                    holder.favoriteButton.setImageResource(android.R.drawable.btn_star_big_on);
                    holder.favoriteButton.setTag(android.R.drawable.btn_star_big_on);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView categoryName;
        ImageButton favoriteButton; // ImageButton 참조 추가

        ViewHolder(View itemView) {
            super(itemView);
            categoryName = itemView.findViewById(R.id.categoryName);
            favoriteButton = itemView.findViewById(R.id.favoriteButton); // ImageButton 초기화
            favoriteButton.setTag(android.R.drawable.btn_star_big_off); // 초기 태그 설정
        }
    }
}