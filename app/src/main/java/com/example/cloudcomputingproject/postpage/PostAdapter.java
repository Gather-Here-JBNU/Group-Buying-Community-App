package com.example.cloudcomputingproject.postpage;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.cloudcomputingproject.ChatActivity;
import com.example.cloudcomputingproject.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {

    private List<PostPreview> postList;
    private String u_id, email;

    public PostAdapter(List<PostPreview> postList, String u_id, String email) {
        this.postList = postList;
        this.u_id = u_id;
        this.email = email;
    }

    @Override
    public PostViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_preview_template, parent, false);

        view.setOnClickListener(v -> {
            int position = (int) v.getTag();

            PostPreview post = postList.get(position);

            //parent로부터 context가져오기
            Context context = parent.getContext();

            //ChatActivity 이동
            Intent in = new Intent(context, ChatActivity.class);
            in.putExtra("u_id", u_id);
            in.putExtra("email", email);
            in.putExtra("title", post.getTitle());
            context.startActivity(in);

        });
        return new PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PostViewHolder holder, int position) {
        PostPreview post = postList.get(position);

        holder.itemView.setTag(position);
        // 게시물 이미지 로딩, 실패 시 기본 이미지 표시
        Picasso.get()
                .load(post.getPostImageUrl())
                .placeholder(R.drawable.default_image)
                .error(R.drawable.default_image)
                .into(holder.postImageView);
        // 프로필 이미지 로딩, 실패 시 기본 이미지 표시
        Picasso.get()
                .load(post.getProfileImageUrl())
                .placeholder(R.drawable.user)
                .error(R.drawable.user)
                .into(holder.profileImageView);
        // 사용자 이름, 제목과 요약 설정
        holder.userNameView.setText(post.getUserName());
        holder.titleView.setText(post.getTitle());
        holder.summaryView.setText(post.getSummary());
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    public static class PostViewHolder extends RecyclerView.ViewHolder {
        ImageView profileImageView;
        TextView userNameView;
        ImageView postImageView;
        TextView titleView;
        TextView summaryView;

        public PostViewHolder(View itemView) {
            super(itemView);
            profileImageView = itemView.findViewById(R.id.userProfileImage); //
            userNameView = itemView.findViewById(R.id.userName);
            postImageView = itemView.findViewById(R.id.postTemplateImageView);
            titleView = itemView.findViewById(R.id.postTitle);
            summaryView = itemView.findViewById(R.id.postSummary);
        }
    }

}