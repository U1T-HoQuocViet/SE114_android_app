package com.example.myapplication.data;

import android.content.Context;

import com.example.myapplication.data.model.FriendRequest;
import com.example.myapplication.data.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FriendRepository {
    Context context;
    public List<FriendRequest> friends = new ArrayList<>();
    UserRepository userRepo;
    public FriendRepository(Context context) {
        this.context = context;
        userRepo = ServiceProvider.getInstance(null).getUserRepository();
    }

    public List<FriendRequest> getFriendRequests() {
        return friends.stream().filter(request ->
                    request.status.equals("pending") &&
                    request.receiverName.equals(userRepo.getCurrentUser().userName))
                .collect(Collectors.toList());
    }


    public void sendFriendRequest(FriendRequest request) {
        request.senderName = userRepo.getCurrentUser().userName;
        friends.add(request);
    }

    public void acceptFriendRequest(FriendRequest request) {
        request.status = "accepted";
    }

    public void declineFriendRequest(FriendRequest request) {
        request.status = "decline";
    }

    public boolean checkRequestSent(User user){
        return friends.stream().anyMatch(request ->
                request.senderName.equals(userRepo.getCurrentUser().userName) &&
                request.receiverName.equals(user.userName)
        );
    }
}
