package com.example.cloudcomputingproject.postlist;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.cloudcomputingproject.ShowPostClickActivity;
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.postlist, parent, false);
        PostViewHolder holder = new PostViewHolder(view);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = (int) v.getTag();

                PostPreview post = postList.get(position);

                //parent로부터 context가져오기
                Context context = parent.getContext();
                String post_id = post.getPost_id();
                //ChatActivity 이동
                Intent intent = new Intent(context, ShowPostClickActivity.class);
                intent.putExtra("post_id", post_id);
                context.startActivity(intent);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(PostViewHolder holder, int position) {
        PostPreview post = postList.get(position);

        holder.itemView.setTag(position);
        holder.title_tv.setText(post.getTitle());
        holder.nickname_tv.setText(post.getNickname());
        // 사용자 닉네임, post 제목 설정
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    public static class PostViewHolder extends RecyclerView.ViewHolder {

        TextView nickname_tv;

        TextView title_tv;

        public PostViewHolder(View itemView) {
            super(itemView);
            nickname_tv = itemView.findViewById(R.id.nickname_tv);
            title_tv = itemView.findViewById(R.id.title_tv);
        }
    }
}