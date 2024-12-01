package com.profile.setupprofilewithai.controllers;

import com.profile.setupprofilewithai.service.ChatGptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/gpt")
public class ChatGptController {

    @Autowired
    private ChatGptService chatGptService;

    @GetMapping("/ask")
    public ResponseEntity<String> ask(@RequestParam String question) {
        return ResponseEntity.ok(chatGptService.askChatGpt(question));
    }
}
