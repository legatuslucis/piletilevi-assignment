package com.app.tickets;

import com.app.tickets.dto.EventDto;
import com.app.tickets.dto.NewTicketsListDto;
import com.app.tickets.exception.TicketAlreadyValidatedException;
import com.app.tickets.exception.TicketNotSoldValidationException;
import com.app.tickets.exception.CodeNotFoundValidationException;
import com.app.tickets.service.EventsService;
import com.app.tickets.service.TicketService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
public class Controller {
    private final EventsService eventsService;
    private final TicketService ticketService;

    private final Logger logger = LoggerFactory.getLogger(Controller.class);

    Controller(@Autowired EventsService eventsService, @Autowired TicketService ticketService) {
        this.eventsService = eventsService;
        this.ticketService = ticketService;
    }

    @GetMapping(produces = "application/json")
    public List<EventDto> getEventsWithStatistics() {
        logger.info("Attempt to get events with ticket statistics");
        return eventsService.getAllEventsWithStatistics();
    }

    @PutMapping(consumes = "text/plain")
    public ResponseEntity<String> validateTicketForEvent(@RequestBody String barCode) {
        logger.info("Attempt to validate ticket by barcode:" + barCode);
        try {
            ticketService.validateTicket(barCode);
        } catch (CodeNotFoundValidationException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (TicketNotSoldValidationException e) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(e.getMessage());
        } catch (TicketAlreadyValidatedException e) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping(value="load-tickets", consumes = "application/json")
    public void loadNewTickets(@RequestBody NewTicketsListDto newTickets) {
        logger.info("Attempt to load " + newTickets.getTickets().size() + " new tickets");
        ticketService.loadTickets(newTickets.getTickets());
    }
}
