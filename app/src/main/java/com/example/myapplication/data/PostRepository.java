package com.example.myapplication.data;

import android.content.Context;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PostRepository {
    Context context;
    UserRepository repository;
    public List<Post> posts = new ArrayList<>();

    public PostRepository(Context context){

        this.context = context;

        //mock posts
        posts.addAll(Arrays.asList(
            new Post(new User("Nhan Tran", "", ""), "Hahahahahahahha"),
            new Post(new User("Quoc Viet", "", ""), "Hahahahahaha"),
            new Post(new User("Miku", "", ""), "Hahahahahaha")
        ));
    }

    public Post addPost(String content, User creator){
        Post post = new Post();
        post.content = content;
        post.creator = creator;
        post.createdAt = LocalDateTime.now();
        posts.add(0, post);

        return post;
    }

    public List<Post> getPosts(){
        return posts;
    }
}
