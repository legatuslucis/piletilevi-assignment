package com.app.tickets.repository;

import com.app.tickets.entity.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TicketsRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<Ticket> getAllTickets() {
        return null;
    }

    public Ticket getTicketById(Long id) {
        return null;
    }

    public void saveTicket(Ticket ticket) {

    }

    public void updateTicket(Ticket ticket) {

    }

    public void deleteTicket(Long id) {

    }
}
