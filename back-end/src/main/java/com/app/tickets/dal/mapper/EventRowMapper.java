package com.app.tickets.dal.mapper;

import com.app.tickets.entity.Event;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class EventRowMapper implements RowMapper<Event> {

    @Override
    public Event mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Event.EventBuilder()
                .setId(rs.getLong("id"))
                .setTitle(rs.getString("title"))
                .setLocation(rs.getString("location"))
                .setPrice(rs.getInt("price"))
                .setDate(rs.getDate("date"))
                .setEventTypeId(rs.getLong("event_type_id"))
                .build();
    }
}
