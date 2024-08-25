package com.mik.backend.api.v1.dtos.request;

import lombok.Builder;

import java.util.Map;

@Builder
public record UserMessageRequest(
        String senderId,
        Map<String, Object> content
) {
}
