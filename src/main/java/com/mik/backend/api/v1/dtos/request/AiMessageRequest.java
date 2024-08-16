package com.mik.backend.api.v1.dtos.request;

import lombok.Builder;

@Builder
public record AiMessageRequest(
        String senderId,
        String content
) {
}
