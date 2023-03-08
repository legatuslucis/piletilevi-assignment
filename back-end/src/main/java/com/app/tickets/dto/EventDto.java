package com.app.tickets.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class EventDto {
    private Long id;
    private String title;
    private Date date;
    private String type;
    private String location;
    private int price;
    private int obtainableTickets;
    private int soldTickets;
    private int validatedTickets;
}
