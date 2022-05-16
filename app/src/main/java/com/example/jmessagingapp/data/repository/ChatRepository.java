package com.example.jmessagingapp.data.repository;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.jmessagingapp.data.model.Chat;
import com.example.jmessagingapp.data.model.ChatDetail;
import com.example.jmessagingapp.data.model.User;
import com.example.jmessagingapp.interfaces.MainListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Vector;

public class ChatRepository {
    private static FirebaseDatabase db = FirebaseDatabase.getInstance();
    private static DatabaseReference chatRef = db.getReference("chat");

    public static String CURRENT_RECEIVER_ID = "";

    public static void insertChat(String receiverId, String senderId, String message){
        String chatId = getChatId(receiverId, senderId);
        String detailId = chatRef.child(chatId).push().getKey();

        ChatDetail chat = new ChatDetail(detailId, receiverId, senderId, message);

        chatRef.child(chatId).child(detailId).setValue(chat.toMap());
    }

    public static void checkNewMessage(){

    }

    private static String getChatId(String receiverId, String senderId){
        return (receiverId.compareTo(senderId) < 0) ? receiverId + senderId : senderId + receiverId;
    }

    public static void getChats(String receiverId, String senderId, MainListener<Chat> listener) {
        String chatId = getChatId(receiverId, senderId);
        Query query = chatRef.orderByKey().equalTo(chatId);
//        Log.d("CHAT ID", chatRef.orderByKey().equalTo(chatId).)
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
//                Log.d("AAA", "BBB");
                Chat chat = new Chat(chatId, transformAllSnapshot(snapshot));
                if (listener != null)
                    listener.onFinish(chat, null);
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

    }

    private static List<ChatDetail> transformAllSnapshot(DataSnapshot snapshot) {
        if(!snapshot.exists()) return null;


        Vector<ChatDetail> chatDetails = new Vector<>();
        snapshot = snapshot.getChildren().iterator().next();
        Iterator<DataSnapshot> snapshots = snapshot.getChildren().iterator();
        while(snapshots.hasNext()){
            snapshot = snapshots.next();

            String receiverId, senderId, message;
            receiverId = snapshot.child("receiverId").getValue(String.class);
            senderId = snapshot.child("senderId").getValue(String.class);
            message = snapshot.child("message").getValue(String.class);

            Log.d("SENDER CHAT", senderId);

            Date timestamp = (new Date(snapshot.child("timestamp").getValue(Long.class)));
            SimpleDateFormat sdf = new SimpleDateFormat("MMM dd\nHH:mm", Locale.getDefault());

            chatDetails.add(new ChatDetail(snapshot.getKey(), receiverId, senderId, message, sdf.format(timestamp)));
//            chatDetails.add(new ChatDetail(snapshot.getKey(), receiverId, senderId, message, "time"));
        }
        return chatDetails;
    }
}
