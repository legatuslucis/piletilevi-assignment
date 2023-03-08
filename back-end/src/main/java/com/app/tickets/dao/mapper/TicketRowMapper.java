package com.app.tickets.dao.mapper;

import com.app.tickets.entity.Ticket;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class TicketRowMapper implements RowMapper<Ticket> {

    @Override
    public Ticket mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Ticket.TicketBuilder()
                .setId(rs.getLong("id"))
                .setValidationCode(rs.getString("validation_code"))
                .setTicketStatusId(rs.getLong("ticket_status_id"))
                .setEventId(rs.getLong("event_id"))
                .build();
    }
}
