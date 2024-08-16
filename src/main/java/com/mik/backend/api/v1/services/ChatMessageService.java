package com.mik.backend.api.v1.services;

import com.mik.backend.api.v1.dtos.base.ChatMessageDTO;
import com.mik.backend.api.v1.dtos.request.UserMessageRequest;

import java.util.List;

public interface ChatMessageService {

    ChatMessageDTO createAiMessage(ChatMessageDTO savedUserMessageDTO);

    ChatMessageDTO saveUserTextMessage(UserMessageRequest userMessageRequest);

    ChatMessageDTO saveUserAudioMessage(UserMessageRequest userMessageRequest);

    List<ChatMessageDTO> getAllMessages(String senderId);

}
