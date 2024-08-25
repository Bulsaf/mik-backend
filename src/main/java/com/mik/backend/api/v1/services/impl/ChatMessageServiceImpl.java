package com.mik.backend.api.v1.services.impl;

import com.mik.backend.api.v1.clients.SpeechKitClient;
import com.mik.backend.api.v1.dtos.base.ChatMessageDTO;
import com.mik.backend.api.v1.dtos.request.UserMessageRequest;
import com.mik.backend.api.v1.dtos.response.ChatMessageResponse;
import com.mik.backend.api.v1.dtos.response.SpeechKitResponse;
import com.mik.backend.api.v1.exceptions.BadRequestException;
import com.mik.backend.api.v1.mappers.ChatMessageMapper;
import com.mik.backend.api.v1.services.AIChatService;
import com.mik.backend.api.v1.services.ChatMessageService;
import com.mik.backend.storage.entities.ChatMessageEntity;
import com.mik.backend.storage.repositories.ChatMessageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Base64;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatMessageServiceImpl implements ChatMessageService {

    private final ChatMessageRepository chatMessageRepository;
    private final ChatMessageMapper chatMessageMapper;

    private final SpeechKitClient speechKitClient;
    private final AIChatService aiChatService;

    @Override
    @Transactional
    public ChatMessageResponse createAiMessage(ChatMessageDTO savedUserMessageDTO) {

        Map<String, Object> generatedContent = aiChatService.getResponse(savedUserMessageDTO.senderId(),
                savedUserMessageDTO.content().get("message").toString());

        ChatMessageEntity chatMessageEntity = ChatMessageEntity.builder()
                .senderId("mik")
                .recipientId(savedUserMessageDTO.senderId())
                .content(generatedContent)
                .build();

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

        String recognizedMessage = recognisedMessageFromRequest(userMessageRequest);
        Map<String, Object> newMessageContent = Map.of(
                "message", recognizedMessage,
                "type","audio"
        );

        log.info(newMessageContent.toString());

        ChatMessageEntity chatMessageEntity = ChatMessageEntity.builder()
                .senderId(userMessageRequest.senderId())
                .recipientId("mik")
                .content(newMessageContent)
                .build();

        return chatMessageMapper.toDto(chatMessageRepository.save(chatMessageEntity));
    }

    private String recognisedMessageFromRequest(UserMessageRequest userMessageRequest) {

        log.info(userMessageRequest.content().toString());

        byte[] audioMessageFromBase64 = Base64.getMimeDecoder()
                .decode(userMessageRequest.content().get("audioURL").toString());

        SpeechKitResponse speechKitResponse = speechKitClient.recognition(audioMessageFromBase64)
                .orElseThrow(()-> new BadRequestException("Speech Kit is not recognized"));

        log.info(speechKitResponse.result());

        return speechKitResponse.result().substring(0,speechKitResponse.result().length()/2+1);
    }

}
