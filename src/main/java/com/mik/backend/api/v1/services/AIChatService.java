package com.mik.backend.api.v1.services;

import jakarta.annotation.Nonnull;

import java.util.Map;

public interface AIChatService {

    @Nonnull
    Map<String, Object> getResponse(@Nonnull String userId, @Nonnull String request);

}
