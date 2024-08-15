package com.mik.backend.api.v1.services.impl;

import com.mik.backend.api.v1.dtos.base.ChatMessageDTO;
import com.mik.backend.api.v1.dtos.base.ChatRoomDTO;
import com.mik.backend.api.v1.exceptions.BadRequestException;
import com.mik.backend.api.v1.mappers.ChatMessageMapper;
import com.mik.backend.api.v1.services.ChatMessageService;
import com.mik.backend.api.v1.services.ChatRoomService;
import com.mik.backend.storage.entities.ChatMessageEntity;
import com.mik.backend.storage.repositories.ChatMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatMessageServiceImpl implements ChatMessageService {

    private final ChatMessageRepository chatMessageRepository;
    private final ChatMessageMapper chatMessageMapper;

    private final ChatRoomService chatRoomService;

    @Value("${services.ai-service-url}")
    private String aiServiceUrl;

    @Override
    public ChatMessageDTO createMessage(ChatMessageDTO savedUserMessageDTO) {

        ChatMessageDTO generatedMessage = getGeneratedMessageFromAi(savedUserMessageDTO)
                .orElseThrow(() -> new BadRequestException("Message is not generated"));

        ChatMessageEntity chatMessageEntity = chatMessageMapper.toEntity(generatedMessage);
        chatMessageEntity.setSenderId("mik");
        chatMessageEntity.setRecipientId(savedUserMessageDTO.senderId());

        ChatMessageDTO createdMessage = chatMessageMapper
                .toDto(chatMessageRepository.save(chatMessageEntity));

        return createdMessage;
    }

    @Override
    public ChatMessageDTO saveMessage(ChatMessageDTO userMessageRequest) {

        ChatRoomDTO chatRoomDTO = chatRoomService
                .getChatRoomBySenderIdAndRecipientId(
                        userMessageRequest.senderId(),
                        userMessageRequest.recipientId(),
                        true
                );

        ChatMessageEntity chatMessageEntity = chatMessageMapper.toEntity(userMessageRequest);
        chatMessageEntity.setChatId(chatRoomDTO.id());

        return chatMessageMapper.toDto(chatMessageRepository.save(chatMessageEntity));
    }

    public List<ChatMessageDTO> getAllMessages(String senderId) {
        ChatRoomDTO chatRoomDTO = chatRoomService.getChatRoomBySenderIdAndRecipientId(
                senderId,
                "mik",
                false
        );

        if (chatRoomDTO == null) {
            throw new BadRequestException("Chat room not found");
        }

        return chatMessageRepository.findAllByChatId(chatRoomDTO.id()).stream()
                .map(chatMessageMapper::toDto)
                .collect(Collectors.toList());
    }

    private Optional<ChatMessageDTO> getGeneratedMessageFromAi(ChatMessageDTO savedUserMessageDTO) {

        /*URI requestUri = URI.create(aiServiceUrl + "/api/v1/generate");

        var response = RestClient.create()
                .post()
                .uri(requestUri)
                .body(savedUserMessageDTO)
                .retrieve()
                .toEntity(ChatMessageDTO.class);

        return Optional
                .ofNullable(response.getBody());*/
        return Optional.ofNullable(savedUserMessageDTO);
    }

}
