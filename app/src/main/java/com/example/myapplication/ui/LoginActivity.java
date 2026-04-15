package com.example.myapplication.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

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

public class LoginActivity extends AppCompatActivity {
    TextView registerNavLink;
    Button signInButton;

    UserRepository repo = ServiceProvider.getInstance(this).getUserRepository();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.login_activity);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        registerNavLink = findViewById(R.id.registerNavLink);
        signInButton = findViewById(R.id.signInButton);

        registerNavLink.setOnClickListener(view -> {
            Intent intent = new Intent(this, RegisterActivity.class);
            startActivity(intent);
        });

        signInButton.setOnClickListener(view -> {
            TextInputEditText emailInput = findViewById(R.id.log_email);
            TextInputEditText passwordInput = findViewById(R.id.log_password);

            if(ServiceProvider.getInstance(this).getUserRepository().login(
                emailInput.getText().toString(),
                passwordInput.getText().toString()
            ) == null){
                return;
            }

            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
        });

    }
}