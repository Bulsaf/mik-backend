package com.mik.backend.api.v1.dtos.base;

import lombok.Builder;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;

@Builder
public record ChatMessageDTO(
        UUID id,
        UUID chatId,
        String senderId,
        String recipientId,
        Map<String, Object> message,
        Instant createdAt
) {
}
