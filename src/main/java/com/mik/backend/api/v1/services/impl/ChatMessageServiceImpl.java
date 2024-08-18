package com.mik.backend.api.v1.services.impl;

import com.mik.backend.api.v1.clients.MikAiClient;
import com.mik.backend.api.v1.clients.SpeechKitClient;
import com.mik.backend.api.v1.dtos.base.ChatMessageDTO;
import com.mik.backend.api.v1.dtos.request.UserMessageRequest;
import com.mik.backend.api.v1.dtos.response.ChatMessageResponse;
import com.mik.backend.api.v1.dtos.response.SpeechKitResponse;
import com.mik.backend.api.v1.exceptions.BadRequestException;
import com.mik.backend.api.v1.mappers.ChatMessageMapper;
import com.mik.backend.api.v1.services.ChatMessageService;
import com.mik.backend.storage.entities.ChatMessageEntity;
import com.mik.backend.storage.repositories.ChatMessageRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatMessageServiceImpl implements ChatMessageService {

    private final ChatMessageRepository chatMessageRepository;
    private final ChatMessageMapper chatMessageMapper;

    private final SpeechKitClient speechKitClient;
    private final MikAiClient mikAiClient;

    private final Logger logger = LoggerFactory.getLogger(ChatMessageServiceImpl.class);

    @Override
    public List<ChatMessageDTO> getAllMessages(String senderId) {

        return chatMessageRepository.findAllBySenderId(senderId).stream()
                .map(chatMessageMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public ChatMessageResponse createAiMessage(ChatMessageDTO savedUserMessageDTO) {

        ChatMessageDTO generatedMessage = mikAiClient.getGeneratedMessageFromAi(savedUserMessageDTO)
                .orElseThrow(() -> new BadRequestException("Message is not generated"));

        ChatMessageEntity chatMessageEntity = chatMessageMapper.toEntity(generatedMessage);
        chatMessageEntity.setSenderId("mik");
        chatMessageEntity.setRecipientId(savedUserMessageDTO.senderId());

        ChatMessageDTO createdMessage = chatMessageMapper
                .toDto(chatMessageRepository.save(chatMessageEntity));

        return ChatMessageResponse.builder()
                .content(createdMessage.content())
                .build();
    }

    @Override
    @Transactional
    public ChatMessageDTO saveUserTextMessage(UserMessageRequest userMessageRequest) {

        if (userMessageRequest.content() == null){
            throw new BadRequestException("Message is not writing");
        }

        ChatMessageDTO chatMessageDTO = ChatMessageDTO.builder()
                .senderId(userMessageRequest.senderId())
                .recipientId("mik")
                .content(userMessageRequest.content())
                .build();

        ChatMessageEntity chatMessageEntity = chatMessageMapper.toEntity(chatMessageDTO);

        return chatMessageMapper.toDto(chatMessageRepository.save(chatMessageEntity));
    }

    @Override
    @Transactional
    public ChatMessageDTO saveUserAudioMessage(UserMessageRequest userMessageRequest) {

        if (userMessageRequest.content() == null){
            throw new BadRequestException("Message is not writing");
        }

        logger.info(userMessageRequest.content().toString());

        byte[] audioMessageFromBase64 = Base64.getMimeDecoder()
                .decode(userMessageRequest.content().get("audioURL").toString());

        logger.info(Arrays.toString(audioMessageFromBase64));

        SpeechKitResponse transMessage = speechKitClient.recognition(audioMessageFromBase64)
                .orElseThrow(()-> new BadRequestException("Speech Kit is not recognized"));

        Map<String, Object> newMessageContent = Map.of(
                "audioURL", userMessageRequest.content().get("audioURL").toString(),
                "message", transMessage.result(),
                "type","audio"
        );

        ChatMessageEntity chatMessageEntity = ChatMessageEntity.builder()
                .senderId(userMessageRequest.senderId())
                .recipientId("mik")
                .content(newMessageContent)
                .build();

        return chatMessageMapper.toDto(chatMessageRepository.save(chatMessageEntity));
    }

}
