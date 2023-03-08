package com.app.tickets.dao;

import com.app.tickets.dao.mapper.TicketRowMapper;
import com.app.tickets.entity.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Optional;

@Component
public class TicketDao implements Dao<Ticket>{
    private final JdbcTemplate jdbcTemplate;
    private final TicketRowMapper ticketRowMapper;

    TicketDao(@Autowired JdbcTemplate jdbcTemplate, @Autowired TicketRowMapper ticketRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.ticketRowMapper = ticketRowMapper;
    }

    @Override
    public Optional<Ticket> get(long id) {
        String query = "SELECT * FROM ticket WHERE id=?";
        return Optional.ofNullable(jdbcTemplate.queryForObject(query, ticketRowMapper, id));
    }

    @Override
    public Collection<Ticket> getAll() {
        String query = "SELECT * FROM ticket";
        return jdbcTemplate.query(query, ticketRowMapper);
    }

    @Override
    public void save(Ticket entity) {
        String query = "INSERT INTO ticket (validation_code, ticket_status_id, event_id) VALUES (?, ?, ?)";
        jdbcTemplate.update(query, entity.getValidationCode(), entity.getTicketStatusId(), entity.getEventId());
    }

    @Override
    public void update(Ticket entity) {
        String query = "UPDATE ticket SET ticket_status_id=? WHERE id=?";
        jdbcTemplate.update(query, entity.getTicketStatusId(), entity.getId());
    }

    @Override
    public void delete(Ticket entity) {
        String query = "DELETE FROM ticket WHERE id=?";
        jdbcTemplate.update(query, entity.getId());
    }

    public Optional<Integer> countSoldTickets(Long eventId) {
        String query = "SELECT COUNT(*) FROM ticket WHERE event_id=? AND (ticket_status_id=1 OR ticket_status_id=2)";
        return Optional.ofNullable(jdbcTemplate.queryForObject(query, Integer.class, eventId));
    }

    public Optional<Integer> countValidatedTickets(Long eventId) {
        String query = "SELECT COUNT(*) FROM ticket WHERE event_id=? AND ticket_status_id=2";
        return Optional.ofNullable(jdbcTemplate.queryForObject(query, Integer.class, eventId));
    }

    public Optional<Integer> countObtainableTickets(Long eventId) {
        String query = "SELECT COUNT(*) FROM ticket WHERE event_id=? AND ticket_status_id=0";
        return Optional.ofNullable(jdbcTemplate.queryForObject(query, Integer.class, eventId));
    }

    public Optional<Ticket> getTicketByValidationCode(String validationCode) {
        String query = "SELECT * FROM ticket WHERE validation_code=?";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(query, ticketRowMapper, validationCode));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }
}
