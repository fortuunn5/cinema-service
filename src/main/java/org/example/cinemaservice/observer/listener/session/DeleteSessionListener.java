package org.example.cinemaservice.observer.listener.session;

import lombok.RequiredArgsConstructor;
import org.example.cinemaservice.observer.event.session.DeleteSessionEvent;
import org.example.cinemaservice.service.ReservationService;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeleteSessionListener {
    private final ReservationService reservationService;

    @EventListener
    @Order(5)
    public void deleteReservations(DeleteSessionEvent deleteSessionEvent) {
        if (deleteSessionEvent.getSessionId() == null) {
            throw new IllegalArgumentException("Seat id is null");
        }
        if (!reservationService.getAllReservationsBySessionId(deleteSessionEvent.getSessionId()).isEmpty()) {
            throw new IllegalArgumentException("Seat has reservations");
        }
    }
}
