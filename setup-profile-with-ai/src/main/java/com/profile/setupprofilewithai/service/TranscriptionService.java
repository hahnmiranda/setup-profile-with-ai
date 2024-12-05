package com.profile.setupprofilewithai.service;

import com.profile.setupprofilewithai.domain.Transcription;
import com.profile.setupprofilewithai.repositories.TranscriptionRepository;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;

@Service
public class TranscriptionService {

    @Autowired
    private TranscriptionRepository repository;
    @Autowired
    private ChatGptService chatGptService;

    public String gptAnswerByPrompt(MultipartFile file) throws Exception {
        // Salva o arquivo temporariamente
        File tempFile = File.createTempFile("uploaded-", ".pdf");
        file.transferTo(tempFile);

        if(validateHash(tempFile))
            return getAnswareByHash(tempFile);

        String prompt = createPrompt(file, tempFile);

        String answerFromGpt = chatGptService.askChatGpt(prompt);

        saveContentAnswerOnTxt(answerFromGpt, file);

        // Exclui o arquivo temporário
        tempFile.delete();

        return answerFromGpt;
    }

    public String createPrompt(MultipartFile file, File tempFile) throws Exception {
        // Extrai o texto usando o serviço
        String extractedText = extractTextFromPdf(tempFile);

        // Adiciona o prompt ao conteúdo do pdf
        extractedText = addPrompt(extractedText);

        saveContentPromptOnTxt(extractedText, file);

        Transcription transcription = new Transcription(1L, 1L, getHashFromFile(tempFile), getPromptFilePathAndNameWithoutExtension(file.getOriginalFilename()));
        repository.save(transcription);

        return extractedText;
    }

    // retorna resposta já construida pela AI
    private String getAnswareByHash(File file) throws IOException {
        String name = repository.findAll().stream()
                .filter(hash-> {
                    try {
                        return hash.getFileHash().equals(getHashFromFile(file));
                    } catch (IOException | NoSuchAlgorithmException e) {
                        throw new RuntimeException(e);
                    }
                }).map(Transcription::getFileName).findFirst().orElse("");

        File answerContent = ResourceUtils.getFile(name + "-answer.txt");

        Jsonb jsonb = JsonbBuilder.create();
//        User user = jsonb.fromJson(json, User.class);

        return new String(Files.readAllBytes(Paths.get(answerContent.getPath())));
    }

    private String getHashFromFile(File file) throws IOException, NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        try (FileInputStream fis = new FileInputStream(file)) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                md.update(buffer, 0, bytesRead);
            }
        }
        byte[] md5Bytes = md.digest();
        StringBuilder sb = new StringBuilder();
        for (byte b : md5Bytes) {
            sb.append(String.format("%02x", b));
        }

        return sb.toString();
    }

    private boolean validateHash(File file) throws Exception {
        StringBuilder sb = new StringBuilder(getHashFromFile(file));

        List<String> updatedHashs = repository.findAll().stream().map(Transcription::getFileHash).toList();

        if(updatedHashs.contains(sb.toString()))
           return true;

        return false;
    }

    public String extractTextFromPdf(File pdfFile) throws IOException {
        try (PDDocument document = PDDocument.load(pdfFile)) {
            PDFTextStripper pdfStripper = new PDFTextStripper();
            return pdfStripper.getText(document);
        }
    }

    public String addPrompt(String extractedText) throws IOException {
        StringBuilder prompt = new StringBuilder();

        // Localiza o arquivo dentro de src/main/resources
        File file = ResourceUtils.getFile("classpath:prompt/start-prompt.txt");

        // Lê o conteúdo do arquivo e converte em String
        String content = new String(Files.readAllBytes(Paths.get(file.getPath())));

        prompt.append(content);
        prompt.append("\n\n");
        prompt.append(extractedText);
        prompt.append("\n\n");

        file = ResourceUtils.getFile("classpath:prompt/finish-prompt.txt");
        content = new String(Files.readAllBytes(Paths.get(file.getPath())));
        prompt.append(content);

        return prompt.toString();
    }

    public void saveContentPromptOnTxt(String extractedText, MultipartFile file) {
        // Caminho onde o arquivo será salvo no projeto
        String filePath = getPromptFilePathAndNameWithoutExtension(file.getOriginalFilename()) + "-prompt.txt";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            // Escreve o conteúdo no arquivo
            writer.write(extractedText);
            System.out.println("Arquivo salvo com sucesso em: " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveContentAnswerOnTxt(String extractedText, MultipartFile file) {
        // Caminho onde o arquivo será salvo no projeto
        String filePath = getPromptFilePathAndNameWithoutExtension(file.getOriginalFilename()) + "-answer.txt";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            // Escreve o conteúdo no arquivo
            writer.write(extractedText);
            System.out.println("Arquivo salvo com sucesso em: " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getPromptFilePathAndNameWithoutExtension(String originalFilename) {
        if (originalFilename == null || originalFilename.isEmpty())
            return ""; // Retorna uma string vazia se o nome do arquivo for nulo ou vazio

        int lastDotIndex = originalFilename.lastIndexOf(".");

        if (lastDotIndex == -1)
            return originalFilename; // Retorna o nome completo se não houver ponto

        String projectPath = Paths.get("").toAbsolutePath().toString();

        return projectPath + "/src/main/resources/output/" + originalFilename.substring(0, lastDotIndex);
    }
}
