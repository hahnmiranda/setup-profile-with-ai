package com.profile.setupprofilewithai.dto;

import java.util.Collections;
import java.util.List;

public class ChatGptRequest {
    private String model;
    private List<Message> messages;

    public ChatGptRequest(String model, String content) {
        this.model = model;
        this.messages = Collections.singletonList(new Message("user", content));
    }

    public String getModel() {
        return model;
    }

    public List<Message> getMessages() {
        return messages;
    }

    static class Message {
        private String role;
        private String content;

        public Message(String role, String content) {
            this.role = role;
            this.content = content;
        }

        public String getRole() {
            return role;
        }

        public String getContent() {
            return content;
        }
    }
}
