package com.example.myapplication.ui.component;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.myapplication.R;
import com.example.myapplication.data.Post;

import java.time.format.DateTimeFormatter;

public class PostComponent extends ConstraintLayout {

    public Post post;

    public TextView nameTag;
    public TextView createDate;
    public TextView contentTextView;
    public ImageView avatarImage;


    public PostComponent(Context context, AttributeSet attrs) {
        super(context, attrs);

        inflate(context, R.layout.post, this);

        nameTag = findViewById(R.id.nameTag);
        createDate = findViewById(R.id.createDate);
        contentTextView = findViewById(R.id.contentView);
        avatarImage = findViewById(R.id.imageView);

    }

    public void setContent(Post post){
        nameTag.setText(post.creator.userName);
        Glide.with(this)
            .load((post.creator.avatarURL != null && !post.creator.avatarURL.isBlank()) ? post.creator.avatarURL : R.drawable.default_avatar)
            .transform(new CenterCrop(), new RoundedCorners(20))
            .into(avatarImage);

        createDate.setText(DateTimeFormatter.ofPattern("dd/MM/yyyy").format(post.createdAt).toString());
        contentTextView.setText(post.content);
    }
}