package com.mik.backend.api.v1.clients;

import com.mik.backend.api.v1.dtos.response.SpeechKitResponse;
import com.mik.backend.api.v1.exceptions.BadRequestException;
import com.mik.backend.configs.SpeechKitConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.net.URI;
import java.util.Arrays;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class SpeechKitClient {

    private final SpeechKitConfig speechKitConfig;

    public Optional<SpeechKitResponse> recognition(byte[] bytes) {

        URI uri = URI.create(String.format(speechKitConfig.getSpeechKitUri()+
                "?topic=%s&lang=%s&format=oggopus&folderId=%s",
                speechKitConfig.getTopic(), speechKitConfig.getLang(), speechKitConfig.getFolderId()));

        var response = RestClient.create()
                .post()
                .uri(uri)
                .header("Authorization", "Api-Key " + speechKitConfig.getApiKey())
                .body(bytes)
                .retrieve()
                .toEntity(SpeechKitResponse.class);

        if (response.getStatusCode() != HttpStatusCode.valueOf(200)){
            throw new BadRequestException("Invalid response code: " + response.getStatusCode());
        }

        return Optional.ofNullable(response.getBody());

    }

}
