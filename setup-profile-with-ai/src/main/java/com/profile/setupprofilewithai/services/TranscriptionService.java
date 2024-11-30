package com.profile.setupprofilewithai.services;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service
public class TranscriptionService {
    public String extractTextFromPdf(File pdfFile) throws IOException {
        try (PDDocument document = PDDocument.load(pdfFile)) {
            PDFTextStripper pdfStripper = new PDFTextStripper();
            return pdfStripper.getText(document);
        }
    }

    public String addPrompt(String extractedText) throws IOException {
        // Localiza o arquivo dentro de src/main/resources
        File file = ResourceUtils.getFile("classpath:prompt/prompt.txt");

        // Lê o conteúdo do arquivo e converte em String
        String content = new String(Files.readAllBytes(Paths.get(file.getPath())));

        return content + "\n\n" + extractedText;
    }

    public void saveContentOnTxt(String extractedText, MultipartFile file) {
        // Caminho onde o arquivo será salvo no projeto
        String filePath = "src/main/resources/output/" + file.getOriginalFilename() + ".txt";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            // Escreve o conteúdo no arquivo
            writer.write(extractedText);
            System.out.println("Arquivo salvo com sucesso em: " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
