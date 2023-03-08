package com.app.tickets.service;

import com.app.tickets.dao.TicketDao;
import com.app.tickets.dto.NewTicketDto;
import com.app.tickets.entity.Ticket;
import com.app.tickets.exception.TicketAlreadyValidatedException;
import com.app.tickets.exception.TicketNotSoldValidationException;
import com.app.tickets.exception.CodeNotFoundValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TicketService {

    private final TicketDao ticketDao;

    TicketService(@Autowired TicketDao ticketDao) {
        this.ticketDao = ticketDao;
    }

    private Ticket convertToEntity(NewTicketDto newTicketDto) {
        return new Ticket.TicketBuilder()
                .setEventId(newTicketDto.getEventId())
                .setTicketStatusId(newTicketDto.getTicketStatusId())
                .setValidationCode(newTicketDto.getValidationCode())
                .build();
    }

    public void loadTickets(List<NewTicketDto> newTickets){
            List<Ticket> tickets = newTickets.stream().map(this::convertToEntity).toList();
            tickets.forEach(ticket -> {
                ticket.setTicketStatusId(ticket.getTicketStatusId());
                try {
                    ticketDao.save(ticket);
                } catch (Exception ignored) {}
            });
    }

    public void validateTicket(String barcode) throws CodeNotFoundValidationException,
            TicketNotSoldValidationException, TicketAlreadyValidatedException {
        Optional<Ticket> opTicket = ticketDao.getTicketByValidationCode(barcode);
        if (opTicket.isEmpty()) {
            throw new CodeNotFoundValidationException("Ticket is not found");
        } else if (opTicket.get().getTicketStatusId() == 0L) {
            throw new TicketNotSoldValidationException("Ticket is not sold");
        } else if (opTicket.get().getTicketStatusId() == 2L) {
            throw new TicketAlreadyValidatedException("Ticket is already validated");
        }
        Ticket ticket = opTicket.get();
        ticket.setTicketStatusId(2L);
        ticketDao.update(ticket);
    }
}
