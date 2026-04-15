package com.example.myapplication;

import android.app.Application;
import android.util.Log;

import com.example.myapplication.data.ServiceProvider;

public class MyApplication extends Application {
    public ServiceProvider serviceProvider;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("AAA", "onCreate called");
        serviceProvider = ServiceProvider.getInstance(this);
    }
}
