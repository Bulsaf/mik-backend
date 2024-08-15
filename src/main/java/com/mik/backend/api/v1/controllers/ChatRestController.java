package com.mik.backend.api.v1.controllers;

import com.mik.backend.api.v1.dtos.base.ChatMessageDTO;
import com.mik.backend.api.v1.dtos.response.UserUUIDResponse;
import com.mik.backend.api.v1.services.ChatMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@CrossOrigin(origins = {"http://localhost:5173"},
        methods = {RequestMethod.GET,RequestMethod.POST,RequestMethod.DELETE,RequestMethod.PUT,RequestMethod.OPTIONS,RequestMethod.HEAD},
        allowCredentials = "*",
        allowedHeaders = "*",
        maxAge = 43200)
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
