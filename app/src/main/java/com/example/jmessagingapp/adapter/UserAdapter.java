package com.example.jmessagingapp.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jmessagingapp.ChatActivity;
import com.example.jmessagingapp.R;
import com.example.jmessagingapp.data.model.Chat;
import com.example.jmessagingapp.data.model.User;
import com.example.jmessagingapp.data.repository.ChatRepository;
import com.example.jmessagingapp.data.repository.UserRepository;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    public List<User> users;

    public UserAdapter(List<User> users){
        this.users = users;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View v = inflater.inflate(R.layout.row_item_user, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        holder.bind(users.get(position));
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView txtName, txtEmail;
        private Button btnChat;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txt_user_name);
            txtEmail = itemView.findViewById(R.id.txt_user_email);
            btnChat = itemView.findViewById(R.id.btn_chat);
        }

        public void bind(User user) {
            txtName.setText(user.getName());
            txtEmail.setText(user.getEmail());
            btnChat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ChatRepository.CURRENT_RECEIVER_ID = user.getId();

                    Intent i = new Intent(v.getContext(), ChatActivity.class);
                    ((Activity) v.getContext()).startActivity(i);
                    Log.d("BUTTON CHAT CLICKED", UserRepository.CURRENT_USER.getName() + " || "  +UserRepository.CURRENT_USER.getId() + " will Start chat with " + user.getName() + " || "  +user.getId());
                }
            });
        }

    }
}
