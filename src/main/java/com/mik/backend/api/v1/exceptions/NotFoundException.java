package com.mik.backend.api.v1.exceptions;

public class NotFoundException extends GlobalException{
    public NotFoundException(String message) {
        super(message);
    }
}
