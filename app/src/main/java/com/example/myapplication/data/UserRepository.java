package com.example.myapplication.data;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class UserRepository {
    Context context;
    public User currentUser;
    public List<User> users = new ArrayList<>();;
    public  UserRepository(Context context){

        this.context = context;

        // mock users
        users.addAll(Arrays.asList(
            new User("Viet", "Bruh", "1"),
            new User("Nhan", "Bruh1", "1"),
            new User("Khang", "Bruh2", "1"))
        );
    }

    public User login(String email, String password){
        User f_user = users.stream().filter(x -> x.email.equals(email))
                .findFirst()
                .orElse(null);
        if(f_user != null){
            if(f_user.password.equals(password)){
                currentUser = f_user;
                return f_user;
            }
        }
        return null;
    }

    public boolean register(String userName, String email, String password, String confirmPassword){
        if(users == null)
            users = new ArrayList<>();

        if(!password.equals(confirmPassword) ){
            return false;
        }
        User n_user = new User();
        n_user.userName = userName;
        n_user.password = password;
        n_user.email = email;

        users.add(n_user);
        return true;
    }

    public User saveProfileData(String address, String avatarURL, String description){
        if(currentUser != null){
            currentUser.address = address;
            currentUser.avatarURL = avatarURL;
            currentUser.description = description;
        }
        return currentUser;
    }

}
