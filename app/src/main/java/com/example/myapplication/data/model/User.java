package com.example.myapplication.data.model;

public class User {
    public  String userName;
    public String email;
    public String phoneNumber;
    public String password;
    public String address;
    public String avatarURL;
    public String description;

    public User(){}

    public User(String userName, String email, String password){
        this.userName = userName;
        this.email = email;
        this.password = password;
    }

    public User(String userName, String phoneNumber, String email, String password){
        this.userName = userName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.password = password;
    }

}
