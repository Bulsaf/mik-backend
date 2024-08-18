package com.mik.backend.configs;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class SpeechKitConfig {

    private final String speechKitUri = "https://stt.api.cloud.yandex.net/speech/v1/stt:recognize";

    @Value("${speech-kit.api-key}")
    private String apiKey;

    private final String lang = "ru-RU";

    private final String topic = "general";

    private final Boolean profanityFilter = false;

    private final Boolean rawResults = false;

    private final String format = "oggopus";

    private final String sampleRateHertz = "48000";

    private final String folderId = "b1giu5kd5icm9odp9ch4";

}
