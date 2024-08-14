package com.mik.backend.api.v1.mappers;

import com.mik.backend.api.v1.dtos.base.ChatRoomDTO;
import com.mik.backend.storage.entities.ChatRoomEntity;
import org.springframework.stereotype.Component;

@Component
public class ChatRoomMapper {

    public ChatRoomDTO toDto(ChatRoomEntity chatRoomEntity) {
        if (chatRoomEntity == null) {
            return null;
        }

        return ChatRoomDTO.builder()
                .id(chatRoomEntity.getId())
                .senderId(chatRoomEntity.getSenderId())
                .recipientId(chatRoomEntity.getRecipientId())
                .build();
    }

    public ChatRoomEntity toEntity(ChatRoomDTO chatRoomDTO) {
        if (chatRoomDTO == null) {
            return null;
        }

        return ChatRoomEntity.builder()
                .id(chatRoomDTO.id())
                .senderId(chatRoomDTO.senderId())
                .recipientId(chatRoomDTO.recipientId())
                .build();
    }

}
