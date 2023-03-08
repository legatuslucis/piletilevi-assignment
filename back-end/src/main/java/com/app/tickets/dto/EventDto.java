package com.app.tickets.dto;

import com.app.tickets.entity.Event;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class EventDto {

    private Long id;
    private String title;
    private Date date;
    private String type;
    private String location;
    private int price;
    private int obtainableTickets;
    private int soldTickets;
    private int validatedTickets;

    public EventDto(EventDtoBuilder builder) {
        this.id = builder.id;
        this.title = builder.title;
        this.type = builder.type;
        this.location = builder.location;
        this.price = builder.price;
        this.obtainableTickets = builder.obtainableTickets;
        this.soldTickets = builder.soldTickets;
        this.validatedTickets = builder.validatedTickets;
        this.date = builder.date;
    }

    public static class EventDtoBuilder {
        private Long id;
        private String title;
        private Date date;
        private String type;
        private String location;
        private int price;
        private int obtainableTickets;
        private int soldTickets;
        private int validatedTickets;


        public EventDtoBuilder setId(Long id) {
            this.id = id;
            return this;
        }

        public EventDtoBuilder setTitle(String title) {
            this.title = title;
            return this;
        }

        public EventDtoBuilder setType(String type) {
            this.type = type;
            return this;
        }

        public EventDtoBuilder setLocation(String location) {
            this.location = location;
            return this;
        }

        public EventDtoBuilder setPrice(Integer price) {
            this.price = price;
            return this;
        }

        public EventDtoBuilder setObtainableTickets(Integer obtainableTickets) {
            this.obtainableTickets = obtainableTickets;
            return this;
        }

        public EventDtoBuilder setSoldTickets(Integer soldTickets) {
            this.soldTickets = soldTickets;
            return this;
        }

        public EventDtoBuilder setValidatedTickets(Integer validatedTickets) {
            this.validatedTickets = validatedTickets;
            return this;
        }

        public EventDtoBuilder setDate(Date date) {
            this.date = date;
            return this;
        }

        public EventDto build() {
            return new EventDto(this);
        }

    }

}
