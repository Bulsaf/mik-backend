package com.mik.backend.api.v1.dtos.request;

import lombok.Builder;

@Builder
public record AiMessageRequest(
        String user_input,
        String user_id
) {
}
