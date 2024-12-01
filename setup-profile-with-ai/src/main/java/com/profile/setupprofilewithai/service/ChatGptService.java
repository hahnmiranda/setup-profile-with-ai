package com.profile.setupprofilewithai.service;

import com.profile.setupprofilewithai.dto.ChatGptRequest;
import com.profile.setupprofilewithai.dto.ChatGptResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Collections;
import java.util.Objects;

@Service
public class ChatGptService {
    private final WebClient webClient;

    @Value("${openai.api.url}")
    private String apiUrl;

    @Value("${openai.api.key}")
    private String apiKey;

    public ChatGptService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
    }

    public String askChatGpt(String question) {
        ChatGptRequest request = new ChatGptRequest("gpt-3.5-turbo", question);

        ChatGptResponse response = webClient.post()
                .uri(apiUrl)
                .header("Authorization", "Bearer " + apiKey)
                .header("Content-Type", "application/json")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(ChatGptResponse.class)
                .block();

        if (response == null || response.getChoices() == null || response.getChoices().isEmpty()) {
            return "Erro: Resposta da API está vazia ou inválida.";
        }

        ChatGptResponse.Choice choice = response.getChoices().get(0);
        if (choice.getMessage() == null || choice.getMessage().getContent() == null) {
            return "Erro: Mensagem retornada pela API está vazia.";
        }

        // testApi(request, question);

        return choice.getMessage().getContent();
    }

    private void testApi(ChatGptRequest request, String question) {
        String rawResponse = webClient.post()
                .uri(apiUrl)
                .header("Authorization", "Bearer " + apiKey)
                .header("Content-Type", "application/json")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        System.out.println("Resposta bruta da API: " + rawResponse);
    }
}
