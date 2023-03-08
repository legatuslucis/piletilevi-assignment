package com.app.tickets.service;

import com.app.tickets.dao.EventDao;
import com.app.tickets.dao.TicketDao;
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

        EventDto eventDto = new EventDto();
        eventDto.setId(event.getId());
        eventDto.setTitle(event.getTitle());
        eventDto.setDate(event.getDate());
        eventDto.setLocation(event.getLocation());
        eventDto.setPrice(event.getPrice());
        eventDto.setObtainableTickets(obtainableTickets.orElse(0));
        eventDto.setValidatedTickets(validatedTickets.orElse(0));
        eventDto.setSoldTickets(soldTickets.orElse(0));
        eventDto.setType(type);

        return eventDto;
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
