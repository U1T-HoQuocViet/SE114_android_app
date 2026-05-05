package com.example.myapplication.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.myapplication.R;
import com.example.myapplication.data.Post;
import com.example.myapplication.data.PostRepository;
import com.example.myapplication.data.ServiceProvider;
import com.example.myapplication.data.UserRepository;
import com.example.myapplication.ui.component.PostAdapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class HomeActivity extends AppCompatActivity {

    TextView contentTextView;
    EditText edtSearch;
    Button postButton, optionMenuBtn;
    ListView postContainer;
    ImageView btnOptionMenu;

    PostAdapter postAdapter;
    List<Post> feedPosts;

    PostRepository postRepo;
    UserRepository userRepo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        postRepo = ServiceProvider.getInstance(this).getPostRepository();
        userRepo = ServiceProvider.getInstance(this).getUserRepository();

        contentTextView = findViewById(R.id.postContentTextView);
        postButton = findViewById(R.id.postButton);
        postContainer = findViewById(R.id.postContainer);
        edtSearch = findViewById(R.id.edtSearch);

        btnOptionMenu = findViewById(R.id.btnOptionMenu);

        feedPosts = new ArrayList<>(postRepo.getPosts());


        postAdapter = new PostAdapter(this, feedPosts);
        postContainer.setAdapter(postAdapter);

        bindActions();

        registerForContextMenu(postContainer);
    }

    void bindActions(){
        btnOptionMenu.setOnClickListener(v -> {
            PopupMenu popup = new PopupMenu(this, v);
            popup.getMenuInflater().inflate(R.menu.feed_menu, popup.getMenu());
            popup.setOnMenuItemClickListener(item -> {
                int id = item.getItemId();
                if (id == R.id.menu_profile) {
                    startActivity(new Intent(this, ProfileActivity.class));
                    return true;
                } else if (id == R.id.menu_sort_date) {
                    sortByDate();
                    postAdapter.notifyDataSetInvalidated();
                    return true;
                } else if (id == R.id.menu_sort_author) {
                    isSortedByUser = !isSortedByUser;
                    sortByAuthor();
                    postAdapter.notifyDataSetInvalidated();
                    return true;
                }
                return false;
            });
            popup.show();
        });

        postButton.setOnClickListener(v -> {
            Post p = postRepo.addPost(contentTextView.getText().toString(), userRepo.currentUser);
            feedPosts.add(p);
            postAdapter.notifyDataSetChanged();
        });


        postContainer.setOnItemLongClickListener((parent, view, position, v_id) -> {
            PopupMenu popup = new PopupMenu(this, view);

            popup.getMenuInflater().inflate(R.menu.post_menu, popup.getMenu());
            popup.setOnMenuItemClickListener(item -> {
                int id = item.getItemId();
                if (id == R.id.menu_detail) {
                    feedPosts.get(position).isVisible = true;
                    postAdapter.notifyDataSetChanged();
                    return true;
                } else if (id == R.id.menu_hide) {
                    feedPosts.get(position).isVisible = false;
                    postAdapter.notifyDataSetChanged();
                    return true;
                }
                return false;
            });
            popup.show();
            return true;
        });


        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterPosts(s.toString());
            }
            @Override
            public void afterTextChanged(Editable s) {}
        });
    }
    boolean isSortedByDate = false;
    boolean isSortedByUser = false;
    private void filterPosts(@NonNull String query) {
        feedPosts.clear();
        feedPosts.addAll(postRepo.getPosts().stream().filter(p ->
                (p.content.toLowerCase().contains(query.toLowerCase()) ||
                p.creator.userName.toLowerCase().contains(query.toLowerCase())))
                .collect(Collectors.toList()));
        if(isSortedByDate) sortByDate();
        if(isSortedByUser) sortByAuthor();
        postAdapter.notifyDataSetInvalidated();

    }

    private void sortByDate() {
        Collections.sort(feedPosts, (p1, p2) ->
            p1.createdAt.compareTo(p2.createdAt)
        );
    }

    private void sortByAuthor() {
        Collections.sort(feedPosts, (p1, p2) ->
                p1.creator.userName.compareToIgnoreCase(p2.creator.userName)
        );
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.post_menu, menu);
    }
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int position = info.position;
        if (position < 0) return false;

        Post selectedPost = feedPosts.get(position);

        int id = item.getItemId();
        if (id == R.id.menu_detail) {
            Toast.makeText(this, "Detail: " + selectedPost.content, Toast.LENGTH_SHORT).show();
            return true;
        } else if (id == R.id.menu_hide) {
            Toast.makeText(this, "Post hidden", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onContextItemSelected(item);
    }


}