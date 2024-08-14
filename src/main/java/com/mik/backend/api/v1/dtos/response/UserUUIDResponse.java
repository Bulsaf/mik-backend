package com.mik.backend.api.v1.dtos.response;

import lombok.Builder;

import java.util.UUID;

@Builder
public record UserUUIDResponse(
        UUID uuid
) {
}
