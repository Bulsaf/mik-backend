package com.mik.backend.api.v1.dtos.base;

import lombok.Builder;

import java.util.UUID;

@Builder
public record ChatRoomDTO(
    UUID id,
    String senderId,
    String recipientId
) {
}
