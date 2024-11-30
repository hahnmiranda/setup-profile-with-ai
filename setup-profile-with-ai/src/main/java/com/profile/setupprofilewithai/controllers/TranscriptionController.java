package com.profile.setupprofilewithai.controllers;

import com.profile.setupprofilewithai.services.TranscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

@RestController
@RequestMapping("/api/transcription/v1")
public class TranscriptionController {

    @Autowired
    private TranscriptionService service;

    @PostMapping("/extract-text")
    public ResponseEntity<String> extractTextFromPdf(@RequestParam("file") MultipartFile file) {
        try {
            // Salva o arquivo temporariamente
            File tempFile = File.createTempFile("uploaded-", ".pdf");
            file.transferTo(tempFile);

            // Extrai o texto usando o serviço
            String extractedText = service.extractTextFromPdf(tempFile);

            // Adiciona o prompt ao conteúdo do pdf
            extractedText = service.addPrompt(extractedText);

            service.saveContentOnTxt(extractedText, file);

            // Exclui o arquivo temporário
            tempFile.delete();

            return ResponseEntity.ok(extractedText);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao processar o arquivo PDF: " + e.getMessage());
        }
    }
}
