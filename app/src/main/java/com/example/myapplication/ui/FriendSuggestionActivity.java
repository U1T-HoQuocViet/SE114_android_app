package com.example.myapplication.ui;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.data.FriendRepository;
import com.example.myapplication.data.ServiceProvider;
import com.example.myapplication.data.UserRepository;
import com.example.myapplication.data.model.User;
import com.example.myapplication.ui.adapter.FriendSuggestionAdapter;

import java.util.ArrayList;
import java.util.List;

public class FriendSuggestionActivity extends AppCompatActivity {

    private RecyclerView rvSuggestions;
    private ImageView backBtn;

    private FriendSuggestionAdapter adapter;
    private List<User> suggestions = new ArrayList<>();
    private List<String> myPhoneContacts = new ArrayList<>();
    UserRepository userRepo;
    FriendRepository friendRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_suggestion);

        userRepo = ServiceProvider.getInstance(this).getUserRepository();
        friendRepository = ServiceProvider.getInstance(this).getFriendRepository();

        backBtn = findViewById(R.id.backBtn);
        backBtn.setOnClickListener(v -> finish());

        rvSuggestions = findViewById(R.id.rvSuggestions);
        rvSuggestions.setLayoutManager(new LinearLayoutManager(this));

        adapter = new FriendSuggestionAdapter(suggestions, friendRepository);

        rvSuggestions.setAdapter(adapter);

        checkPermissionAndLoad();
    }

    private void checkPermissionAndLoad() {
        ActivityResultLauncher<String> permissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    loadSuggestions();
                } else {
                    Toast.makeText(this, "Cần quyền danh bạ để gợi ý bạn bè", Toast.LENGTH_SHORT).show();
                }
            });
        permissionLauncher.launch(Manifest.permission.READ_CONTACTS);
//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)
//                == PackageManager.PERMISSION_GRANTED) {
//            loadSuggestions();
//        } else {
//            permissionLauncher.launch(Manifest.permission.READ_CONTACTS);
//        }
    }

    private void loadSuggestions() {
        if (TextUtils.isEmpty(userRepo.getCurrentUser().phoneNumber)) {
            Toast.makeText(this, "Thiếu số điện thoại người dùng hiện tại", Toast.LENGTH_SHORT).show();
            return;
        }

        loadContacts();

        suggestions.clear();
        suggestions.addAll(userRepo.getAllUserByPhoneContacts(myPhoneContacts));
        adapter.notifyDataSetChanged();

        if (suggestions.isEmpty()) {
            Toast.makeText(this, "Không có gợi ý phù hợp", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadContacts() {

        Cursor cursor = getContentResolver().query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                null, null, null, null
        );

        if (cursor == null) return;

        int nameIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
        int numberIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);

        while (cursor.moveToNext()) {
            String name = cursor.getString(nameIndex);
            String phone = cursor.getString(numberIndex);
            if (!TextUtils.isEmpty(phone)) {
                myPhoneContacts.add(phone);
            }
        }

        cursor.close();
    }
}