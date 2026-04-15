package com.example.myapplication.data;

import android.content.Context;

public class ServiceProvider {
    private static ServiceProvider instance;
    private final Context context;

    // Repositories
    private UserRepository userRepository;
    private PostRepository postRepository;

    private ServiceProvider(Context context) {
        // Use applicationContext to avoid memory leaks
        this.context = context.getApplicationContext();
    }

    public static synchronized ServiceProvider getInstance(Context context) {
        if (instance == null) {
            instance = new ServiceProvider(context);
        }
        return instance;
    }

    public UserRepository getUserRepository() {
        if (userRepository == null) {
            userRepository = new UserRepository(context);
        }
        return userRepository;
    }

    public PostRepository getPostRepository() {
        if (postRepository == null) {
            postRepository = new PostRepository(context);
        }
        return postRepository;
    }
}