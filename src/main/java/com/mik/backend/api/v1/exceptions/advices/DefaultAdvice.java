package com.mik.backend.api.v1.exceptions.advices;

import com.mik.backend.api.v1.exceptions.BadRequestException;
import com.mik.backend.api.v1.exceptions.NotFoundException;
import com.mik.backend.api.v1.exceptions.error_messages.DefaultErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class DefaultAdvice {

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<DefaultErrorMessage> handleException(BadRequestException e) {
        DefaultErrorMessage response = new DefaultErrorMessage(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<DefaultErrorMessage> handleException(NotFoundException e) {
        DefaultErrorMessage response = new DefaultErrorMessage(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

}
