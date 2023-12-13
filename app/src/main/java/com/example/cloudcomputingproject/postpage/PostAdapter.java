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
import com.example.cloudcomputingproject.PostActivity;
import com.example.cloudcomputingproject.R;
import com.example.cloudcomputingproject.ShowPostClickActivity;
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.postlist, parent, false);

        view.setOnClickListener(v -> {
            int position = (int) v.getTag();

            PostPreview post = postList.get(position);

            //parent로부터 context가져오기
            Context context = parent.getContext();
            String post_id = post.getPostID();

            //게시글 액티비티 이동
            Intent in = new Intent(context, ShowPostClickActivity.class);
           // in.putExtra("u_id", u_id);
           // in.putExtra("email", email);
           // in.putExtra("title", post.getTitle());
            in.putExtra("post_id", post_id);
            // PostID도 넘겨주고 intent 시작
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
                .placeholder(R.drawable.none)
                .error(R.drawable.none)
                .into(holder.img_iv);
        // 사용자 이름, 제목과 요약 설정
        holder.nickname_tv.setText(post.getUserName());
        holder.title_tv.setText(post.getTitle());
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    public static class PostViewHolder extends RecyclerView.ViewHolder {
        ImageView profileImageView;
        TextView nickname_tv;

        TextView title_tv;

        ImageView img_iv;

        public PostViewHolder(View itemView) {
            super(itemView);
            nickname_tv = itemView.findViewById(R.id.nickname_tv);
            title_tv = itemView.findViewById(R.id.title_tv);
            img_iv = itemView.findViewById(R.id.img_iv);
        }
    }

}