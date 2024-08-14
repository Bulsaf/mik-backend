package com.mik.backend.api.v1.services;

import com.mik.backend.api.v1.dtos.base.ChatRoomDTO;

import java.util.UUID;

public interface ChatRoomService {

    ChatRoomDTO getChatRoomBySenderIdAndRecipientId(String senderId, String recipientId, boolean createdIfNotExist);

    ChatRoomDTO createChatRoom(String userId);

}
