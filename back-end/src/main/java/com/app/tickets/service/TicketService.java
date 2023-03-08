package com.app.tickets.service;

import com.app.tickets.dal.TicketDao;
import com.app.tickets.dto.NewTicketDto;
import com.app.tickets.entity.Ticket;
import com.app.tickets.exception.TicketIsAlreadyValidatedException;
import com.app.tickets.exception.ValidationTicketNotSoldException;
import com.app.tickets.exception.ValidatonCodeNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TicketService {

    private final TicketDao ticketDao;

    TicketService(@Autowired TicketDao ticketDao) {
        this.ticketDao = ticketDao;
    }

    private Ticket convertToEntity(NewTicketDto newTicketDto) {
        return new Ticket.TicketBuilder()
                .setEventId(newTicketDto.getEventId())
                .setValidationCode(newTicketDto.getValidationCode())
                .build();
    }

    public void loadTickets(NewTicketDto[] newTicketDtoArray){
        List<Ticket> tickets = Arrays.stream(newTicketDtoArray).map(this::convertToEntity).toList();
        tickets.forEach(ticket -> {
            ticket.setTicketStatusId(0L);
            ticketDao.save(ticket);
        });
    }

    public void validateTicket(String barCode)
            throws ValidatonCodeNotFoundException, ValidationTicketNotSoldException, TicketIsAlreadyValidatedException {
        Optional<Ticket> opTicket = ticketDao.getTicketByValidationCode(barCode);
        System.out.println(barCode);
        if (opTicket.isEmpty()) {
            throw new ValidatonCodeNotFoundException("Ticket is not found");
        } else if (opTicket.get().getTicketStatusId() == 0L) {
            throw new ValidationTicketNotSoldException("Ticket is not sold");
        } else if (opTicket.get().getTicketStatusId() == 2L) {
            throw new TicketIsAlreadyValidatedException("Ticket is already validated");
        }
        opTicket.get().setTicketStatusId(2L);
        ticketDao.update(opTicket.get());
    }
}
