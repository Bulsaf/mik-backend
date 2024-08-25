package com.mik.backend.api.v1.services.impl;

import com.mik.backend.api.v1.clients.MikAiClient;
import com.mik.backend.api.v1.services.AIChatService;
import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class AIChatServiceImpl implements AIChatService {

    private final MikAiClient mikAiClient;

    @Nonnull
    @Override
    public Map<String, Object> getResponse(@Nonnull String userId, @Nonnull String request) {
        return mikAiClient.getResponse(userId, request);
    }
}
