package com.example.myapplication.ui.adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.data.FriendRepository;
import com.example.myapplication.data.model.FriendRequest;
import com.example.myapplication.data.model.User;

import java.util.List;

public class FriendSuggestionAdapter extends RecyclerView.Adapter<FriendSuggestionAdapter.ViewHolder> {


    private final List<User> suggestions;
    FriendRepository friendRepo;

    public FriendSuggestionAdapter(List<User> list, FriendRepository friendRepository) {
        this.suggestions = list;
        this.friendRepo = friendRepository;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_friend_suggestion, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        User item = suggestions.get(position);

        holder.tvName.setText(item.userName);
        holder.tvPhone.setText(item.phoneNumber);

        Glide.with(holder.itemView.getContext())
                .load(item.avatarURL)
                .placeholder(android.R.drawable.ic_menu_gallery)
                .circleCrop()
                .into(holder.imgAvatar);

        if(friendRepo.checkRequestSent(item)){
            holder.btnAdd.setText("Đã gửi");
            holder.btnAdd.setEnabled(false);
        }else{
            holder.btnAdd.setText("Kết bạn");
            holder.btnAdd.setEnabled(true);
        }

        holder.btnAdd.setOnClickListener(v -> {
            FriendRequest request = new FriendRequest(
                null,
                item.userName,
                "pending",
                System.currentTimeMillis()
            );
            friendRepo.sendFriendRequest(request);
            Toast.makeText(holder.itemView.getContext(), "Đã gửi lời mời kết bạn", Toast.LENGTH_SHORT).show();
            notifyItemChanged(position);
        });
    }

    @Override
    public int getItemCount() {
        return suggestions.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgAvatar;
        TextView tvName, tvPhone;
        Button btnAdd;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgAvatar = itemView.findViewById(R.id.imgAvatar);
            tvName = itemView.findViewById(R.id.tvName);
            tvPhone = itemView.findViewById(R.id.tvPhone);
            btnAdd = itemView.findViewById(R.id.btnAdd);
        }
    }
}