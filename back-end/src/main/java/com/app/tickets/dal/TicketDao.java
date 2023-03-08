package com.app.tickets.dal;

import com.app.tickets.dal.mapper.TicketRowMapper;
import com.app.tickets.entity.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Optional;

@Component
public class TicketDao implements Dao<Ticket>{
    private JdbcTemplate jdbcTemplate;
    private TicketRowMapper ticketRowMapper;

    private final String SQL_GET_ALL = "SELECT * FROM ticket WHERE id=?";
    private final String SQL_GET_TICKET = "SELECT * FROM ticket";
    private final String SQL_UPDATE_TICKET = "UPDATE ticket SET ticket_status_id=?";
    private final String SQL_SAVE_TICKET = "INSERT INTO ticket (validation_code, ticket_status_id, event_id) VALUES (?, ?, ?, ?)";
    private final String SQL_DELETE_TICKET= "DELETE FROM ticket WHERE id=?";
    private final String SQL_COUNT_SOLD_TICKETS_PER_EVENT = "SELECT COUNT(*) FROM ticket WHERE event_id=? AND (ticket_status_id=1 OR ticket_status_id=2)";
    private final String SQL_COUNT_VALIDATED_TICKETS_PER_EVENT = "SELECT COUNT(*) FROM ticket WHERE event_id=? AND ticket_status_id=2";
    private final String SQL_GET_TICKET_BY_VALIDATION_CODE = "SELECT * FROM ticket WHERE validation_code=?";

    TicketDao(@Autowired JdbcTemplate jdbcTemplate, @Autowired TicketRowMapper ticketRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.ticketRowMapper = ticketRowMapper;
    }

    @Override
    public Optional<Ticket> get(long id) {
        return Optional.ofNullable(jdbcTemplate
                .queryForObject(SQL_GET_TICKET, ticketRowMapper, id));
    }

    @Override
    public Collection<Ticket> getAll() {
        return jdbcTemplate.query(SQL_GET_ALL, ticketRowMapper);
    }

    @Override
    public void save(Ticket entity) {
        jdbcTemplate.update(SQL_SAVE_TICKET, entity.getValidationCode(), entity.getTicketStatusId(), entity.getEventId());
    }

    @Override
    public void update(Ticket entity) {
        jdbcTemplate.update(SQL_UPDATE_TICKET, entity.getTicketStatusId());
    }

    @Override
    public void delete(Ticket entity) {
        jdbcTemplate.update(SQL_DELETE_TICKET, entity.getId());
    }

    public Optional<Integer> countSoldTickets(Long eventId) {
        return Optional.ofNullable(jdbcTemplate
                .queryForObject(SQL_COUNT_SOLD_TICKETS_PER_EVENT, Integer.class, eventId));
    }

    public Optional<Integer> countValidatedTickets(Long eventId) {
        return Optional.ofNullable(jdbcTemplate
                .queryForObject(SQL_COUNT_VALIDATED_TICKETS_PER_EVENT, Integer.class, eventId));
    }

    public Optional<Integer> countObtainableTickets(Long eventId) {
        String query = "SELECT COUNT(*) FROM ticket WHERE event_id=? AND ticket_status_id=0";
        return Optional.ofNullable(jdbcTemplate
                .queryForObject(query, Integer.class, eventId));
    }

    public Optional<Ticket> getTicketByValidationCode(String validationCode) {
        try {
            return Optional.ofNullable(jdbcTemplate
                    .queryForObject(SQL_GET_TICKET_BY_VALIDATION_CODE, ticketRowMapper, validationCode));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }
}
