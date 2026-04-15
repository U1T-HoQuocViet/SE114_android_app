package com.example.myapplication.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.myapplication.R;
import com.example.myapplication.data.ServiceProvider;
import com.example.myapplication.data.UserRepository;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class RegisterActivity extends AppCompatActivity {
    private Button createButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.register_activity);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        createButton = findViewById(R.id.createButton);
        createButton.setOnClickListener(view -> {

            TextInputEditText nameInput = findViewById(R.id.reg_name);
            TextInputEditText emailInput = findViewById(R.id.reg_email);
            TextInputEditText passwordInput = findViewById(R.id.reg_password);
            TextInputEditText confirmPasswordInput = findViewById(R.id.reg_confirm_password);

            if(!ServiceProvider.getInstance(this).getUserRepository().register(
                nameInput.getText().toString(),
                emailInput.getText().toString(),
                passwordInput.getText().toString(),
                confirmPasswordInput.getText().toString())){
                    return;
            }
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        });

    }
}