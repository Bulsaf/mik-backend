package com.mik.backend.api.v1.controllers;

import com.mik.backend.api.v1.dtos.base.ChatMessageDTO;
import com.mik.backend.api.v1.dtos.response.UserUUIDResponse;
import com.mik.backend.api.v1.services.ChatMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ChatRestController {

    private final ChatMessageService chatMessageService;

    @GetMapping("/messages/{senderId}")
    List<ChatMessageDTO> getAllMessages(@PathVariable("senderId") String senderId) {
        return chatMessageService.getAllMessages(senderId);
    }

    @GetMapping("/get-uuid")
    public UserUUIDResponse getUserUUID(){
        return new UserUUIDResponse(UUID.randomUUID());
    }

}
