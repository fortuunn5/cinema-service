package org.example.cinemaservice.observer.listener.seat;

import lombok.RequiredArgsConstructor;
import org.example.cinemaservice.observer.event.seat.DeleteSeatEvent;
import org.example.cinemaservice.service.ReservationService;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeleteSeatListener {
    private final ReservationService reservationService;

    @EventListener
    @Order(5)
    public void deleteReservations(DeleteSeatEvent deleteSeatEvent) {
        if (deleteSeatEvent.getSeatId() == null) {
            throw new IllegalArgumentException("Seat id is null");
        }
        if (!reservationService.getAllReservationsBySeatId(deleteSeatEvent.getSeatId()).isEmpty()) {
            throw new IllegalArgumentException("Seat has reservations");
        }
    }
}
