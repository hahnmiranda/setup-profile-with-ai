package com.profile.setupprofilewithai.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ChatGptResponse {
    private List<Choice> choices;
    private Usage usage;

    public List<Choice> getChoices() {
        return choices;
    }

    public void setChoices(List<Choice> choices) {
        this.choices = choices;
    }

    public Usage getUsage() {
        return usage;
    }

    public void setUsage(Usage usage) {
        this.usage = usage;
    }

    public static class Choice {
        private Message message;

        public Message getMessage() {
            return message;
        }

        public void setMessage(Message message) {
            this.message = message;
        }
    }

    public static class Usage {
        @JsonProperty("total_tokens")
        private String totalTokens;

        public String getTotalTokens() {
            return totalTokens;
        }

        public void setTotalTokens(String totalTokens) {
            this.totalTokens = totalTokens;
        }
    }

    public static class Message {
        private String role;
        private String content;

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
}
