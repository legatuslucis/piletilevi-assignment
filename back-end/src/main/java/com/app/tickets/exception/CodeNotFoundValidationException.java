package com.app.tickets.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(HttpStatus.NOT_FOUND)
public class CodeNotFoundValidationException extends Exception {
    private final String message;
    public CodeNotFoundValidationException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
