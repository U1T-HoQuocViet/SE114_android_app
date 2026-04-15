package com.example.myapplication.data;

public class User {
    public  String userName;
    public String email;
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

}
