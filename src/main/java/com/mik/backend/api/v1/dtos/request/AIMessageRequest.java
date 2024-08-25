package com.mik.backend.api.v1.dtos.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record AIMessageRequest(
        @JsonProperty("user_id")
        String userId,
        @JsonProperty("user_input")
        String userInput
) {
}
