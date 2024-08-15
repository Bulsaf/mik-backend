package com.mik.backend.api.v1.controllers;

import com.mik.backend.api.v1.dtos.base.ChatMessageDTO;
import com.mik.backend.api.v1.services.ChatMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMethod;

@CrossOrigin(origins = "http://localhost:5173",
        allowCredentials = "true",
        allowedHeaders = "*",
        maxAge = 43200)
@Controller
@RequiredArgsConstructor
public class ChatBotController {

    private final SimpMessagingTemplate messagingTemplate;
    private final ChatMessageService chatMessageService;

    @MessageMapping("/chat")
    public void processMessage(@Payload final ChatMessageDTO userMessageRequest) {

        ChatMessageDTO savedUserMessageDTO = chatMessageService.saveMessage(userMessageRequest);

        ChatMessageDTO chatBotMessageResponse = chatMessageService.createMessage(savedUserMessageDTO);

        messagingTemplate.convertAndSendToUser(
                userMessageRequest.senderId(),
                "/queue/messages",
                chatBotMessageResponse
        );
    }

}
