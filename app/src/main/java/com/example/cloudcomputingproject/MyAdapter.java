package com.example.cloudcomputingproject;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private static ArrayList<Chat> mDataset;
    String stMyEmail = "";

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder)
     */
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        public TextView nickname;
        public TextView time;
        public AppCompatImageView profile;
        public MyViewHolder(View v) {
            super(v);
            // Define click listener for the ViewHolder's View
            textView = v.findViewById(R.id.tvChat);
            nickname = v.findViewById(R.id.tvNickname);
            time = v.findViewById(R.id.tvTime);
            profile = v.findViewById(R.id.ivProfile);

            profile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    Context context = textView.getContext();
                    Intent in = new Intent(context, MyPage2.class);
                    in.putExtra("user_id", mDataset.get(position).getUserId());

                    if(position != RecyclerView.NO_POSITION){
                        String userEmail = mDataset.get(position).getEmail();
                        in.putExtra("email",userEmail);
                        context.startActivity(in);
                    }
                }
            });
        }
    }

    @Override
    public int getItemViewType(int position) {
        //return super.getItemViewType(position);
        if(mDataset.get(position).email.equals(stMyEmail)){
            return 1;
        } else {
            return 2;
        }
    }

    /**
     * Initialize the dataset of the Adapter
     *
     * @param myDataset String[] containing the data to populate views to be used
     * by RecyclerView
     */
    public MyAdapter(ArrayList<Chat> myDataset, String stEmail) {
        mDataset = myDataset;
        this.stMyEmail = stEmail;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Create a new view, which defines the UI of the list item
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.text_view_left, parent, false);
        if(viewType == 1){
            v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.text_view_right, parent, false);
        }
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        holder.textView.setText(mDataset.get(position).getText());
        holder.nickname.setText(mDataset.get(position).getNickname());
        holder.time.setText(mDataset.get(position).getTime());
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
