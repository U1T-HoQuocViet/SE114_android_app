package com.example.myapplication.ui.component;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.myapplication.R;
import com.example.myapplication.data.Post;

import java.time.format.DateTimeFormatter;

//public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {
//
//    Post mPost;
//    public PostAdapter(Post post){
//        mPost = post;
//    }
//    public static class ViewHolder extends RecyclerView.ViewHolder {
//        public TextView userTV, contentTV, createDateTV;
//        public ImageView avatarImgView;
//        public ViewHolder(View view) {
//            super(view);
//            userTV = view.findViewById(R.id.nameTag);
//            contentTV = view.findViewById(R.id.contentView);
//            createDateTV = view.findViewById(R.id.createDate);
//            avatarImgView = view.findViewById(R.id.user_avatar);
//        }
//    }
//
//    @NonNull
//    @Override
//    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        // Create a new view, which defines the UI of the list item
//        View view = LayoutInflater.from(parent.getContext())
//                .inflate(R.layout.post, parent);
//
//        return new ViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        holder.userTV.setText(mPost.creator.userName);
//        Glide.with(holder.itemView)
//                .load((mPost.creator.avatarURL != null && !mPost.creator.avatarURL.isBlank()) ? mPost.creator.avatarURL : R.drawable.default_avatar)
//                .transform(new CenterCrop(), new RoundedCorners(20))
//                .into(holder.avatarImgView);
//
//        holder.createDateTV.setText(DateTimeFormatter.ofPattern("dd/MM/yyyy").format(mPost.createdAt).toString());
//        holder.contentTV.setText(mPost.content);
//    }
//
//    @Override
//    public int getItemCount() {
//        return 0;
//    }
//}


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.List;

public class PostAdapter extends ArrayAdapter<Post> {

    public PostAdapter(Context context, List<Post> posts) {
        super(context, 0, posts);
    }
    public TextView userTV, contentTV, createDateTV;
    public ImageView avatarImgView;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.post, parent, false);
        }

        userTV = convertView.findViewById(R.id.nameTag);
        contentTV = convertView.findViewById(R.id.contentView);
        createDateTV = convertView.findViewById(R.id.createDate);
        avatarImgView = convertView.findViewById(R.id.user_avatar);

        bindView(convertView, position);

        return convertView;
    }

    void bindView(View view, int position){
        Post mPost = getItem(position);

        userTV.setText(mPost.creator.userName);
        Glide.with(view)
                .load((mPost.creator.avatarURL != null && !mPost.creator.avatarURL.isBlank()) ? mPost.creator.avatarURL : R.drawable.default_avatar)
                .transform(new CenterCrop(), new RoundedCorners(20))
                .into(avatarImgView);

        createDateTV.setText(DateTimeFormatter.ofPattern("dd/MM/yyyy").format(mPost.createdAt).toString());
        contentTV.setText(mPost.content);

        contentTV.setVisibility(mPost.isVisible ? View.VISIBLE : View.GONE);
    }

    public void hidePost(int position){

    }

    public void detailPost(){

    }
}