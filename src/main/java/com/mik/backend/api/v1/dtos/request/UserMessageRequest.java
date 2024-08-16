package com.mik.backend.api.v1.dtos.request;

import lombok.Builder;

import java.util.Map;
import java.util.UUID;

@Builder
public record UserMessageRequest(
        String senderId,
        String recipientId,
        Map<String, Object> content
) {
}
