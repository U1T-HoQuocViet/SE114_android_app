package com.example.myapplication.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.data.ServiceProvider;
import com.example.myapplication.data.User;
import com.example.myapplication.data.UserRepository;
import com.google.android.material.textfield.TextInputEditText;

public class ProfileActivity extends AppCompatActivity {
    Button logOutButton, saveButton;
    TextInputEditText nameInput, emailInput, addressInput, avatarURLInput, descriptionInput;
    void loadProfileUI(){
        logOutButton = findViewById(R.id.logOutButton);
        saveButton = findViewById(R.id.saveProfileButton);

        nameInput = findViewById(R.id.profile_name);
        emailInput = findViewById(R.id.profile_email);
        addressInput = findViewById(R.id.profile_address);
        avatarURLInput = findViewById(R.id.profile_avatarurl);
        descriptionInput = findViewById(R.id.profile_description);

        avatarImg = findViewById(R.id.profile_avatar);
    }
    ImageView avatarImg;

    UserRepository userRepo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.profile_activity);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        loadProfileUI();
        userRepo = ServiceProvider.getInstance(this).getUserRepository();

        if(userRepo.currentUser != null){
            setUserData(userRepo.currentUser);
        }

        logOutButton.setOnClickListener(view -> {
            userRepo.currentUser = null;
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        });

        saveButton.setOnClickListener(view -> {
            userRepo.saveProfileData(
                addressInput.getText().toString(),
                avatarURLInput.getText().toString(),
                descriptionInput.getText().toString()
            );
            Glide.with(this)
                .load(avatarURLInput.getText().toString())
                .into(avatarImg);
        });
    }

    void setUserData(User profileUser){
        nameInput.setText(profileUser.userName);
        emailInput.setText(profileUser.email);
        addressInput.setText(profileUser.address);
        avatarURLInput.setText(profileUser.avatarURL);
        descriptionInput.setText(profileUser.description);

        if(profileUser.avatarURL != null  && !profileUser.avatarURL.isEmpty())
            Glide.with(this)
                .load(profileUser.avatarURL)
                .into(avatarImg);
    }
}