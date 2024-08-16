package com.mik.backend.api.v1.dtos.base;

import lombok.Builder;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;

@Builder
public record ChatMessageDTO(
        UUID id,
        String senderId,
        String recipientId,
        Map<String, Object> content,
        Instant createdAt
) {
}
