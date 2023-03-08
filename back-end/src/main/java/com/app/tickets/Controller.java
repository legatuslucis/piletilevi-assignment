package com.app.tickets;

import com.app.tickets.dto.EventDto;
import com.app.tickets.dto.NewTicketDto;
import com.app.tickets.exception.TicketIsAlreadyValidatedException;
import com.app.tickets.exception.ValidationTicketNotSoldException;
import com.app.tickets.exception.ValidatonCodeNotFoundException;
import com.app.tickets.service.EventsService;
import com.app.tickets.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/")
public class Controller {
    private final EventsService eventsService;
    private final TicketService ticketService;

    Controller(@Autowired EventsService eventsService, @Autowired TicketService ticketService) {
        this.eventsService = eventsService;
        this.ticketService = ticketService;
    }

    @GetMapping(produces = "application/json")
    public List<EventDto> getEventsWithStatistics() {
        return eventsService.getAllEventsWithStatistics();
    }

    @PutMapping(consumes = "text/plain")
    public ResponseEntity<String> validateTicketForEvent(@RequestBody String validationCode) {
        try {
            ticketService.validateTicket(validationCode);
        } catch (ValidatonCodeNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (ValidationTicketNotSoldException e) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(e.getMessage());
        } catch (TicketIsAlreadyValidatedException e) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping(consumes = "application/json")
    public void loadNewTickets(@RequestBody NewTicketDto[] newTicketDtoArray) {
        ticketService.loadTickets(newTicketDtoArray);
    }
}
