package com.mik.backend.api.v1.dtos.response;

import lombok.Builder;

import java.util.Map;

@Builder
public record ChatMessageResponse(
        Map<String, Object> content
) {
}
