package com.app.tickets.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NewTicketDto {
    private Long eventId;
    private String validationCode;
}
