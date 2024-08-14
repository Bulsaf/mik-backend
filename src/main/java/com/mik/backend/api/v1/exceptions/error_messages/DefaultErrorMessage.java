package com.mik.backend.api.v1.exceptions.error_messages;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DefaultErrorMessage {

    private String message;

    public DefaultErrorMessage(String message) {
        this.message = message;
    }
}
