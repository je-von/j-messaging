package com.example.jmessagingapp.adapter;

import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jmessagingapp.HomeActivity;
import com.example.jmessagingapp.MainActivity;
import com.example.jmessagingapp.R;
import com.example.jmessagingapp.data.model.ChatDetail;
import com.example.jmessagingapp.data.model.User;
import com.example.jmessagingapp.data.repository.ChatRepository;
import com.example.jmessagingapp.data.repository.UserRepository;
import com.example.jmessagingapp.interfaces.MainListener;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter {

    public List<ChatDetail> chatDetails;

    private static final int SENDER = 0, RECEIVER = 1;

    public ChatAdapter(List<ChatDetail> chatDetails) {
        this.chatDetails = chatDetails;
    }

    @Override
    public int getItemViewType(int position) {
        ChatDetail chatDetail = chatDetails.get(position);

        return (chatDetail.getSenderId().equals(UserRepository.CURRENT_USER.getId())) ? SENDER : RECEIVER;
    }

    @NonNull
    @NotNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        int resource = (viewType == SENDER) ? R.layout.row_item_chat_sender : R.layout.row_item_chat_receiver;
        View v = inflater.inflate(resource, parent, false);

        return (viewType == SENDER) ? new SenderViewHolder(v) : new ReceiverViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull RecyclerView.ViewHolder holder, int position) {
        ChatDetail chatDetail = chatDetails.get(position);

       if(holder.getItemViewType() == SENDER)
           ((SenderViewHolder) holder).bind(chatDetail);
       else
           ((ReceiverViewHolder) holder).bind(chatDetail);
    }

    @Override
    public int getItemCount() {
        return chatDetails.size();
    }


    public static class SenderViewHolder extends RecyclerView.ViewHolder {

        private TextView txtMessage, txtTimestamp;

        public SenderViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            txtMessage = itemView.findViewById(R.id.txt_chat_message_sender);
            txtTimestamp = itemView.findViewById(R.id.txt_chat_timestamp_sender);
        }

        public void bind(ChatDetail chatDetail) {
            txtMessage.setText(chatDetail.getMessage());
            txtTimestamp.setText(chatDetail.getTimestamp());
        }

    }

    public static class ReceiverViewHolder extends RecyclerView.ViewHolder{

        private TextView txtName, txtMessage, txtTimestamp;

        public ReceiverViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txt_chat_receiver_name);
            txtMessage = itemView.findViewById(R.id.txt_chat_message_receiver);
            txtTimestamp = itemView.findViewById(R.id.txt_chat_timestamp_receiver);
        }

        public void bind(ChatDetail chatDetail) {
            UserRepository.getUserById(ChatRepository.CURRENT_RECEIVER_ID, (data, msg) -> {
                if (data != null) {
                    txtName.setText(data.getName());
                }
            });
            txtMessage.setText(chatDetail.getMessage());
            txtTimestamp.setText(chatDetail.getTimestamp());
        }

    }
}
