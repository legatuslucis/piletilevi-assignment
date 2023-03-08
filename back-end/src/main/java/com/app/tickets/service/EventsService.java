package com.app.tickets.service;

import com.app.tickets.dal.EventDao;
import com.app.tickets.dal.TicketDao;
import com.app.tickets.dto.EventDto;
import com.app.tickets.entity.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EventsService {

    private final EventDao eventDao;
    private final TicketDao ticketDao;

    private EventDto convertEventToDto(Event event) {
        String type = eventDao.getEventTypeName(event.getEventTypeId());
        Optional<Integer> soldTickets = ticketDao.countSoldTickets(event.getId());
        Optional<Integer> validatedTickets = ticketDao.countValidatedTickets(event.getId());
        Optional<Integer> obtainableTickets = ticketDao.countObtainableTickets(event.getId());

        return new EventDto.EventDtoBuilder()
                .setId(event.getId())
                .setTitle(event.getTitle())
                .setType(type)
                .setPrice(event.getPrice())
                .setDate(event.getDate())
                .setLocation(event.getLocation())
                .setObtainableTickets(obtainableTickets.orElse(0))
                .setSoldTickets(soldTickets.orElse(0))
                .setValidatedTickets(validatedTickets.orElse(0))
                .build();
    }

    EventsService(@Autowired EventDao eventDao, @Autowired TicketDao ticketDao) {
        this.eventDao = eventDao;
        this.ticketDao = ticketDao;
    }

    public List<EventDto> getAllEventsWithStatistics() {
        List<Event> events = eventDao.getAll().stream().toList();
        return events.stream().map(this::convertEventToDto).collect(Collectors.toList());
    }
}
