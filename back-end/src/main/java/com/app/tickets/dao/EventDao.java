package com.app.tickets.dao;

import com.app.tickets.dao.mapper.EventRowMapper;
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

    EventDao(@Autowired JdbcTemplate jdbcTemplate,
             @Autowired EventRowMapper eventRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.eventRowMapper = eventRowMapper;
    }

    @Override
    public Optional<Event> get(long id) {
        String query = "SELECT * FROM event WHERE id=?";
        return Optional.ofNullable(jdbcTemplate.queryForObject(query, eventRowMapper, id));
    }

    @Override
    public Collection<Event> getAll() {
        String query = "SELECT * FROM event";
        return jdbcTemplate.query(query, eventRowMapper);
    }

    @Override
    public void save(Event entity) {
        String query = "INSERT INTO event (title, date, location, event_type_id) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(query, entity.getTitle(), entity.getDate(), entity.getLocation(), entity.getEventTypeId());
    }

    @Override
    public void update(Event entity) {
        String query = "UPDATE event SET title=?, date=?, location=?, event_type_id=? WHERE id=?";
        jdbcTemplate.update(query, entity.getTitle(), entity.getDate(), entity.getLocation(), entity.getEventTypeId(), entity.getId());
    }

    @Override
    public void delete(Event entity) {
        String query = "DELETE FROM event WHERE id=?";
        jdbcTemplate.update(query, entity.getId());
    }

    public String getEventTypeName(Long eventId) {
        String query = "SELECT name FROM event_type WHERE id=?";
        return jdbcTemplate.queryForObject(query, String.class, eventId);
    }
}
