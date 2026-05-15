package com.example.myapplication.ui;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.data.FriendRepository;
import com.example.myapplication.data.ServiceProvider;
import com.example.myapplication.data.UserRepository;
import com.example.myapplication.data.model.FriendRequest;
import com.example.myapplication.ui.adapter.FriendRequestAdapter;

import java.util.ArrayList;
import java.util.List;

public class FriendRequestActivity extends AppCompatActivity {

    private RecyclerView rvRequests;
    private ImageView backBtn;

    private FriendRequestAdapter adapter;
    private List<FriendRequest> myRequests = new ArrayList<>();

    UserRepository userRepo;
    FriendRepository friendRepository;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_request);

        userRepo = ServiceProvider.getInstance(this).getUserRepository();
        friendRepository = ServiceProvider.getInstance(this).getFriendRepository();

        rvRequests = findViewById(R.id.rvRequests);
        backBtn = findViewById(R.id.backBtn);
        backBtn.setOnClickListener(v -> finish());

        rvRequests.setLayoutManager(new LinearLayoutManager(this));

        adapter = new FriendRequestAdapter(myRequests, friendRepository);

        rvRequests.setAdapter(adapter);
        loadRequests();
    }

    private void loadRequests() {
        List<FriendRequest> requests = friendRepository.getFriendRequests();
        if(requests.isEmpty()){
            Toast.makeText(this, "Không có lời mời kết bạn nào", Toast.LENGTH_SHORT).show();
        }
        myRequests.clear();
        myRequests.addAll(requests);
        adapter.notifyDataSetChanged();
    }
}