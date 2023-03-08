package com.app.tickets.dal;

import com.app.tickets.dal.mapper.EventRowMapper;
import com.app.tickets.entity.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Optional;

@Component
public class EventDao implements Dao<Event>{
    private final JdbcTemplate jdbcTemplate;
    private final EventRowMapper eventRowMapper;

    private final String SQL_GET_ALL = "SELECT * FROM event";
    private final String SQL_GET_EVENT = "SELECT * FROM event WHERE id=?";
    private final String SQL_SAVE_EVENT = "INSERT INTO event (title, date, location, event_type_id) VALUES (?, ?, ?, ?)";
    private final String SQL_UPDATE_EVENT = "UPDATE event SET title=?, date=?, location=?, event_type_id=? WHERE id=?";
    private final String SQL_DELETE_EVENT = "DELETE FROM event WHERE id=?";

    EventDao(@Autowired JdbcTemplate jdbcTemplate, @Autowired EventRowMapper eventRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.eventRowMapper = eventRowMapper;
    }

    @Override
    public Optional<Event> get(long id) {
        return Optional.ofNullable(jdbcTemplate
                .queryForObject(SQL_GET_EVENT, eventRowMapper, id));
    }

    @Override
    public Collection<Event> getAll() {
        return jdbcTemplate.query(SQL_GET_ALL, eventRowMapper);
    }

    @Override
    public void save(Event entity) {
        jdbcTemplate.update(SQL_SAVE_EVENT, entity.getTitle(), entity.getDate(), entity.getLocation(), entity.getEventTypeId());
    }

    @Override
    public void update(Event entity) {
        jdbcTemplate.update(SQL_UPDATE_EVENT, entity.getTitle(), entity.getDate(), entity.getLocation(), entity.getEventTypeId(), entity.getId());
    }

    @Override
    public void delete(Event entity) {
        jdbcTemplate.update(SQL_DELETE_EVENT);
    }

    public String getEventTypeName(Long eventId) {
        String query = "SELECT name FROM event_type WHERE id=?";
        return jdbcTemplate.queryForObject(query, String.class, eventId);
    }
}
