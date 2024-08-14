package com.mik.backend.api.v1.exceptions;

import java.io.Serializable;

public abstract class GlobalException extends RuntimeException implements Serializable {

    public GlobalException(String message) {
        super(message);
    }

    public GlobalException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
