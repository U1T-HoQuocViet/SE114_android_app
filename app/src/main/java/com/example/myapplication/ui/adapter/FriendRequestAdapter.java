package com.example.myapplication.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.myapplication.R;
import com.example.myapplication.data.FriendRepository;
import com.example.myapplication.data.model.FriendRequest;

import java.util.List;

public class FriendRequestAdapter extends RecyclerView.Adapter<FriendRequestAdapter.ViewHolder> {


    private final List<FriendRequest> myFriendRequests;
    private FriendRepository friendRepository;
    public FriendRequestAdapter(List<FriendRequest> list, FriendRepository friendRepository) {
        this.myFriendRequests = list;
        this.friendRepository = friendRepository;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_friend_request, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FriendRequest request = myFriendRequests.get(position);

        holder.tvName.setText(request.senderName);
        holder.tvPhone.setText(request.receiverName);

        if(request.status.equals("pending")){

        }else if(request.status.equals("accepted")){
            holder.btnAccept.setText("Đã chấp nhận");
            holder.btnAccept.setEnabled(false);
            holder.btnDecline.setEnabled(false);
        }else{
            holder.btnAccept.setText("Đã từ chối");
            holder.btnAccept.setEnabled(false);
            holder.btnDecline.setEnabled(false);
        }

        holder.btnAccept.setOnClickListener(v -> {
            friendRepository.acceptFriendRequest(request);
            notifyItemChanged(position);
        });
        holder.btnDecline.setOnClickListener(v -> {
            friendRepository.declineFriendRequest(request);
            notifyItemChanged(position);
        });
    }

    @Override
    public int getItemCount() {
        return myFriendRequests.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvPhone;
        Button btnAccept, btnDecline;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvPhone = itemView.findViewById(R.id.tvPhone);
            btnAccept = itemView.findViewById(R.id.btnAccept);
            btnDecline = itemView.findViewById(R.id.btnDecline);
        }
    }
}