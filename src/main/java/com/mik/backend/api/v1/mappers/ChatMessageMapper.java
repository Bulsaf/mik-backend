package com.mik.backend.api.v1.mappers;

import com.mik.backend.api.v1.dtos.base.ChatMessageDTO;
import com.mik.backend.storage.entities.ChatMessageEntity;
import org.springframework.stereotype.Component;

@Component
public class ChatMessageMapper {

    public ChatMessageDTO toDto(ChatMessageEntity entity) {
        if (entity == null) return null;

        return ChatMessageDTO.builder()
                .id(entity.getId())
                .chatId(entity.getChatId())
                .senderId(entity.getSenderId())
                .recipientId(entity.getRecipientId())
                .message(entity.getMessage())
                .createdAt(entity.getCreatedAt())
                .build();
    }

    public ChatMessageEntity toEntity(ChatMessageDTO dto) {
        if (dto == null) return null;

        return ChatMessageEntity.builder()
                .id(dto.id())
                .chatId(dto.chatId())
                .senderId(dto.senderId())
                .recipientId(dto.recipientId())
                .message(dto.message())
                .build();
    }

}
