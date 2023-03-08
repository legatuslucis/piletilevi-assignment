package com.app.tickets.dal;

import com.app.tickets.entity.Event;

import java.util.Collection;
import java.util.Optional;

public interface Dao<T> {
    Optional<T> get(long id);
    Collection<T> getAll();
    void save(T entity);
    void update(T entity);
    void delete(T entity);
}
