package com.mik.backend.api.v1.clients;

import com.mik.backend.api.v1.dtos.base.ChatMessageDTO;
import com.mik.backend.api.v1.dtos.request.AiMessageRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class MikAiClient {

    @Value("${services.ai-service-url}")
    private String aiServiceUrl;

    public Optional<ChatMessageDTO> getGeneratedMessageFromAi(ChatMessageDTO savedUserMessageDTO) {

        AiMessageRequest aiMessageRequest = AiMessageRequest.builder()
                .senderId(savedUserMessageDTO.senderId())
                .content(savedUserMessageDTO.content().get("message").toString())
                .build();
        /*URI requestUri = URI.create(aiServiceUrl + "/api/v1/generate");

        var response = RestClient.create()
                .post()
                .uri(requestUri)
                .body(aiMessageRequest)
                .retrieve()
                .toEntity(ChatMessageDTO.class);

        return Optional
                .ofNullable(response.getBody());*/
        return Optional.ofNullable(savedUserMessageDTO);
    }

}
