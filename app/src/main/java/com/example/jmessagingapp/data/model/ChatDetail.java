package com.example.jmessagingapp.data.model;

import com.google.firebase.database.ServerValue;

import java.util.HashMap;

public class ChatDetail{
    private String detailId, receiverId, senderId, message, timestamp;

    public ChatDetail(String detailId, String receiverId, String senderId, String message, String timestamp) {
        this.detailId = detailId;
        this.receiverId = receiverId;
        this.senderId = senderId;
        this.message = message;
        this.timestamp = timestamp;
    }

    public ChatDetail(String detailId, String receiverId, String senderId, String message) {
        this.detailId = detailId;
        this.receiverId = receiverId;
        this.senderId = senderId;
        this.message = message;
        this.timestamp = "";
    }

    public HashMap<String, Object> toMap(){
        HashMap<String, Object> map = new HashMap<>();

        map.put("receiverId", this.receiverId);
        map.put("senderId", this.senderId);
        map.put("message", this.message);
        map.put("timestamp", ServerValue.TIMESTAMP);

        return map;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getDetailId() {
        return detailId;
    }

    public void setDetailId(String detailId) {
        this.detailId = detailId;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
