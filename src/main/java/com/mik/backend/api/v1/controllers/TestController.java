package com.mik.backend.api.v1.controllers;

import com.mik.backend.api.v1.clients.MikAiClient;
import com.mik.backend.api.v1.dtos.base.ChatMessageDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class TestController {

    private final MikAiClient mikAiClient;

    @PostMapping(value = "/api/test",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Map<String, Object> chatMessage(@RequestBody ChatMessageDTO chatMessageDTO) {

        return mikAiClient.getGeneratedMessageFromAi(chatMessageDTO);
    }

}
