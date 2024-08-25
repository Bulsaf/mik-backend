package com.mik.backend.api.v1.clients;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mik.backend.api.v1.dtos.base.ChatMessageDTO;
import com.mik.backend.api.v1.dtos.request.AIMessageRequest;
import com.mik.backend.api.v1.exceptions.BadRequestException;
import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class MikAiClient {

    @Value("${rest-api.ai-chat.generate-uri}")
    private URI aiChatGenerateUri;

    private final RestClient restClient;

    @Nonnull
    public Map<String, Object> getResponse(@Nonnull String userId, @Nonnull String request) {

        final var aiMessageRequest = AIMessageRequest.builder()
                .userId(userId)
                .userInput(request)
                .build();

        ResponseEntity<HashMap> response = restClient.post()
                .uri(aiChatGenerateUri)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(aiMessageRequest)
                .retrieve()
                .toEntity(HashMap.class);

        if (response.getStatusCode() != HttpStatusCode.valueOf(200)) {
            throw new BadRequestException(response.toString());
        }

        return response.getBody();
    }

}
