package com.mik.backend.api.v1.controllers;

import com.mik.backend.api.v1.dtos.base.ChatMessageDTO;
import com.mik.backend.api.v1.dtos.request.UserMessageRequest;
import com.mik.backend.api.v1.dtos.response.ChatMessageResponse;
import com.mik.backend.api.v1.services.ChatMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins = "http://localhost:5173",
        allowCredentials = "true",
        allowedHeaders = "*",
        maxAge = 43200)
@Controller
@RequiredArgsConstructor
public class ChatBotController {

    private final SimpMessagingTemplate messagingTemplate;
    private final ChatMessageService chatMessageService;

    @MessageMapping("/chat/text")
    public void processTextMessage(@Payload final UserMessageRequest userMessageRequest) {

        ChatMessageDTO savedUserMessageDTO = chatMessageService.saveUserTextMessage(userMessageRequest);

        ChatMessageResponse chatBotMessageResponse = chatMessageService.createAiMessage(savedUserMessageDTO);

        messagingTemplate.convertAndSendToUser(
                userMessageRequest.senderId(),
                "/queue/messages",
                chatBotMessageResponse
        );
    }

    @MessageMapping("/chat/audio")
    public void processAudioMessage(@Payload final UserMessageRequest userMessageRequest) {

        ChatMessageDTO savedUserMessageDTO = chatMessageService.saveUserAudioMessage(userMessageRequest);

        ChatMessageResponse chatBotMessageResponse = chatMessageService.createAiMessage(savedUserMessageDTO);

        messagingTemplate.convertAndSendToUser(
                userMessageRequest.senderId(),
                "/queue/messages",
                chatBotMessageResponse
        );
    }

}
