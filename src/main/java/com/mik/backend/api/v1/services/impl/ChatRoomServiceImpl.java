package com.mik.backend.api.v1.services.impl;

import com.mik.backend.api.v1.dtos.base.ChatRoomDTO;
import com.mik.backend.api.v1.exceptions.BadRequestException;
import com.mik.backend.api.v1.mappers.ChatRoomMapper;
import com.mik.backend.api.v1.services.ChatRoomService;
import com.mik.backend.storage.entities.ChatRoomEntity;
import com.mik.backend.storage.repositories.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ChatRoomServiceImpl implements ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;
    private final ChatRoomMapper chatRoomMapper;

    @Override
    public ChatRoomDTO getChatRoomBySenderIdAndRecipientId(String senderId, String recipientId, boolean createdIfNotExist) {

        ChatRoomEntity chatRoomEntity = chatRoomRepository
                .findBySenderIdAndRecipientId(senderId,recipientId)
                .orElse(null);

        if (chatRoomEntity == null && createdIfNotExist) {
            return createChatRoom(senderId);
        }

        return chatRoomMapper.toDto(chatRoomEntity);
    }

    @Override
    public ChatRoomDTO createChatRoom(String userId) {
        if (chatRoomRepository.findBySenderIdAndRecipientId(userId, "mik").isPresent()){
            throw new BadRequestException("Chat room already exists");
        }

        ChatRoomEntity createdChatRoom = chatRoomRepository.save(
                ChatRoomEntity.builder()
                        .senderId(userId)
                        .recipientId("mik")
                        .build()
        );

        return chatRoomMapper.toDto(createdChatRoom);
    }


}
