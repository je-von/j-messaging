package com.example.jmessagingapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.jmessagingapp.adapter.ChatAdapter;
import com.example.jmessagingapp.adapter.UserAdapter;
import com.example.jmessagingapp.data.model.Chat;
import com.example.jmessagingapp.data.model.ChatDetail;
import com.example.jmessagingapp.data.model.User;
import com.example.jmessagingapp.data.repository.ChatRepository;
import com.example.jmessagingapp.data.repository.UserRepository;
import com.example.jmessagingapp.interfaces.MainListener;

import java.util.List;
import java.util.Vector;

public class ChatActivity extends AppCompatActivity implements MainListener<Chat> {

    private RecyclerView rvChats;
    private List<ChatDetail> chats = new Vector<>();
    private ChatAdapter adapter;

    private EditText txtMessage;
    private Button btnSend;

//    public ChatActivity(String receiverId){
//        this.receiverId = receiverId;
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        init();
    }

    private void init() {
        rvChats = findViewById(R.id.rv_chat_contents);

        rvChats.setLayoutManager(new LinearLayoutManager(ChatActivity.this));

        adapter = new ChatAdapter(chats);
        rvChats.setAdapter(adapter);

        ChatRepository.getChats(ChatRepository.CURRENT_RECEIVER_ID, UserRepository.CURRENT_USER.getId(), this);

        txtMessage = findViewById(R.id.txt_message);
        btnSend = findViewById(R.id.btn_send);

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = txtMessage.getText().toString();

                ChatRepository.insertChat(ChatRepository.CURRENT_RECEIVER_ID, UserRepository.CURRENT_USER.getId(), message);
                refreshChat();
            }
        });
    }

    private void refreshChat(){
        finish();
        overridePendingTransition(0, 0);
        startActivity(getIntent());
        overridePendingTransition(0, 0);
    }

    @Override
    public void onFinish(Chat data, String message) {
        chats = data.getChatDetails();
        if(chats != null){
            adapter.chatDetails = chats;
            adapter.notifyDataSetChanged();
        }
    }
}