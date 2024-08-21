package com.mik.backend.api.v1.clients;

import com.mik.backend.api.v1.dtos.base.ChatMessageDTO;
import com.mik.backend.api.v1.dtos.request.AiMessageRequest;
import com.mik.backend.api.v1.exceptions.BadRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
public class MikAiClient {

    @Value("${services.ai-service-url}")
    private String aiServiceUrl;

    private final Logger logger = LoggerFactory.getLogger(MikAiClient.class);

    public Map<String, Object> getGeneratedMessageFromAi(ChatMessageDTO savedUserMessageDTO) {

        logger.info(savedUserMessageDTO.toString());
        AiMessageRequest aiMessageRequest = AiMessageRequest.builder()
                .senderId(savedUserMessageDTO.senderId())
                .content(savedUserMessageDTO.content().get("message").toString())
                .build();

        logger.info(aiMessageRequest.toString());

        URI requestUri = URI.create(aiServiceUrl + "/api/v1/generate");

        var response = RestClient.create()
                .post()
                .uri(requestUri)
                .body(aiMessageRequest)
                .retrieve()
                .toEntity(HashMap.class);

        logger.info(response.toString());
        if (response.getStatusCode() != HttpStatusCode.valueOf(200)){
            throw new BadRequestException(response.toString());
        }

        return response.getBody();
    }

}
