package com.app.tickets.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class NewTicketDto {
    private Long eventId;
    private String validationCode;
    private Long ticketStatusId;
}
