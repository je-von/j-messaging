package com.example.jmessagingapp.data.model;

import java.util.List;

public class Chat {

    private String chatId;
    private List<ChatDetail> chatDetails;

    public Chat(String chatId, List<ChatDetail> chatDetails) {
        this.chatId = chatId;
        this.chatDetails = chatDetails;
    }

    public List<ChatDetail> getChatDetails() {
        return chatDetails;
    }

    public void setChatDetails(List<ChatDetail> chatDetails) {
        this.chatDetails = chatDetails;
    }

    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }
}
