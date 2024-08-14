package com.mik.backend.api.v1.exceptions;

public class BadRequestException extends GlobalException {

    public BadRequestException(String message) {
        super(message);
    }

}
