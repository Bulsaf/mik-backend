package com.mik.backend.api.v1.services;

import com.mik.backend.api.v1.dtos.base.ChatMessageDTO;

import java.util.List;

public interface ChatMessageService {

    ChatMessageDTO createMessage(ChatMessageDTO savedUserMessageDTO);

    ChatMessageDTO saveMessage(ChatMessageDTO userMessageRequest);

    List<ChatMessageDTO> getAllMessages(String senderId);

}
