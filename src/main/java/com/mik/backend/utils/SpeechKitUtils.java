package com.mik.backend.utils;

import com.mik.backend.api.v1.dtos.response.SpeechKitResponse;
import com.mik.backend.configs.SpeechKitConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.net.URI;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class SpeechKitUtils {

    private final SpeechKitConfig speechKitConfig;

    public Optional<SpeechKitResponse> recognition(byte[] bytes) {

        URI uri = URI.create(String.format(speechKitConfig.getSpeechKitUri()+
                "?topic=%s&lang=%s",
                speechKitConfig.getTopic(), speechKitConfig.getLang()));

        var response = RestClient.create()
                .post()
                .uri(uri)
                .header("Authorization", "Api-key " + speechKitConfig.getApiKey())
                .body(bytes)
                .retrieve()
                .toEntity(SpeechKitResponse.class);

        if (response.getStatusCode() == HttpStatusCode.valueOf(200)){
            return Optional.ofNullable(response.getBody());
        }
        else {
            return Optional.empty();
        }

    }

}
