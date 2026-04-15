package com.example.myapplication.ui;

import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.myapplication.R;
import com.example.myapplication.data.Post;
import com.example.myapplication.data.PostRepository;
import com.example.myapplication.data.ServiceProvider;
import com.example.myapplication.data.UserRepository;
import com.example.myapplication.ui.component.PostComponent;

import java.time.LocalDateTime;

public class HomeActivity extends AppCompatActivity {

    TextView contentTextView;
    Button postButton;
    LinearLayout postContainer;

    PostRepository postRepo;
    UserRepository userRepo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        postRepo = ServiceProvider.getInstance(this).getPostRepository();
        userRepo = ServiceProvider.getInstance(this).getUserRepository();

        contentTextView = findViewById(R.id.postContentTextView);
        postButton = findViewById(R.id.postButton);
        postContainer = findViewById(R.id.postContainer);

        postButton.setOnClickListener(view -> {
            postRepo.addPost(contentTextView.getText().toString(), userRepo.currentUser);
            addPostsToView();
        });

        postRepo.getPosts().forEach(post -> {
            PostComponent postComponent = new PostComponent(this, null);
            postComponent.setContent(post);
            postContainer.addView(postComponent);
        });

        addPostsToView();
    }

    void addPostsToView(){
        postContainer.removeAllViews();
        postRepo.getPosts().forEach(post -> {
            addPostToView(post);
        });
    }

    void addPostToView(Post post){
        PostComponent postComponent = new PostComponent(this, null);
        postComponent.setContent(post);
        postContainer.addView(postComponent);
    }



}