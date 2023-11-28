package com.example.cloudcomputingproject.postpage;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.cloudcomputingproject.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {

    private List<PostPreview> postList;

    public PostAdapter(List<PostPreview> postList) {
        this.postList = postList;
    }

    @Override
    public PostViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_preview_template, parent, false);
        return new PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PostViewHolder holder, int position) {
        PostPreview post = postList.get(position);
        // 이미지 로딩 라이브러리를 사용하여 이미지 뷰에 이미지 로딩
        Picasso.get().load(post.getImageUrl()).into(holder.imageView);
        holder.titleView.setText(post.getTitle());
        holder.summaryView.setText(post.getSummary());
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    public static class PostViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView titleView;
        TextView summaryView;

        public PostViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.postTemplateImageView);
            titleView = itemView.findViewById(R.id.postTitle);
            summaryView = itemView.findViewById(R.id.postSummary);
        }
    }
}