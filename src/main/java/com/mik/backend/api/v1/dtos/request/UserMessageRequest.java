package com.mik.backend.api.v1.dtos.request;

import lombok.Builder;

import java.util.UUID;

@Builder
public record UserMessageRequest(
        UUID id,
        String message
) {
}
