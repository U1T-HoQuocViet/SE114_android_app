package com.example.myapplication.data;

import java.time.LocalDateTime;

public class Post {
    public User creator;
    public String content;
    public LocalDateTime createdAt;
    public boolean isVisible = true;
    public Post(){}
    public Post(User creator, String content){
        this.creator = creator;
        this.content = content;
        this.createdAt = LocalDateTime.now();
    }
}
