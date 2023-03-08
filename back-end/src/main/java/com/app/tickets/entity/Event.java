package com.app.tickets.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class Event {
    private Long id;
    private String title;
    private String location;
    private Integer price;
    private Date date;
    private Long eventTypeId;

    public Event(EventBuilder builder) {
        this.id = builder.id;
        this.title = builder.title;
        this.price = builder.price;
        this.date = builder.date;
        this.location = builder.location;
        this.eventTypeId = builder.eventTypeId;
    }

    public static class EventBuilder {
        private Long id;
        private String title;
        private String location;
        private Integer price;
        private Date date;
        private Long eventTypeId;

        public EventBuilder setId(Long id) {
            this.id = id;
            return this;
        }

        public EventBuilder setTitle(String title) {
            this.title = title;
            return this;
        }

        public EventBuilder setLocation(String location) {
            this.location = location;
            return this;
        }

        public EventBuilder setDate(Date date) {
            this.date = date;
            return this;
        }

        public EventBuilder setPrice(Integer price) {
            this.price = price;
            return this;
        }

        public EventBuilder setEventTypeId(Long eventTypeId) {
            this.eventTypeId = eventTypeId;
            return this;
        }

        public Event build() {
            return new Event(this);
        }

    }
}



