package com.app.tickets.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class NewTicketsListDto {
    private List<NewTicketDto> tickets;
}
