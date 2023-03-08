package com.app.tickets.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class TicketAlreadyValidatedException extends Exception{
    private final String message;
    public TicketAlreadyValidatedException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
