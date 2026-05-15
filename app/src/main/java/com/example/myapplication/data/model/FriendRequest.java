package com.example.myapplication.data.model;

public class FriendRequest {
    public String senderName;
    public String receiverName;
    public String status;
    public long timestamp;

    public FriendRequest(String senderName, String receiverName, String status, long timestamp){
        this.senderName = senderName;
        this.receiverName = receiverName;
        this.status = status;
        this.timestamp = timestamp;
    }
}
