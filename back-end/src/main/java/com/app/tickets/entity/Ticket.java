package com.app.tickets.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Ticket {
    private Long id;
    private String validationCode;
    private Long ticketStatusId;
    private Long eventId;

    public Ticket(TicketBuilder builder) {
        this.id = builder.id;
        this.validationCode = builder.validationCode;
        this.ticketStatusId = builder.ticketStatusId;
        this.eventId = builder.eventId;
    }

    public static class TicketBuilder {
        private Long id;
        private String validationCode;
        private Long ticketStatusId;
        private Long eventId;

        public TicketBuilder setId(Long id) {
            this.id = id;
            return this;
        }

        public TicketBuilder setValidationCode(String validationCode) {
            this.validationCode = validationCode;
            return this;
        }

        public TicketBuilder setTicketStatusId(Long ticketStatusId) {
            this.ticketStatusId = ticketStatusId;
            return this;
        }

        public TicketBuilder setEventId(Long eventId) {
            this.eventId = eventId;
            return this;
        }

        public Ticket build() {
            return new Ticket(this);
        }
    }
}
