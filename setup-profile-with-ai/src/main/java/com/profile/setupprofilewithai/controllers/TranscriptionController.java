package com.profile.setupprofilewithai.controllers;

import com.profile.setupprofilewithai.service.TranscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/transcription")
public class TranscriptionController {

    @Autowired
    private TranscriptionService service;

    @PostMapping("/extract-text")
    public ResponseEntity<String> extractTextFromPdf(@RequestParam("file") MultipartFile file) {
        try {
            return ResponseEntity.ok(service.gptAnswerByPrompt(file));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao processar o arquivo PDF: " + e.getMessage());
        }
    }
}
