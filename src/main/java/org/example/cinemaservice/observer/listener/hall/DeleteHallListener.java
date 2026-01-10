package org.example.cinemaservice.observer.listener.hall;

import lombok.RequiredArgsConstructor;
import org.example.cinemaservice.dto.ReservationDto;
import org.example.cinemaservice.observer.event.hall.DeleteHallEvent;
import org.example.cinemaservice.service.ReservationService;
import org.example.cinemaservice.service.SeatService;
import org.example.cinemaservice.service.SessionService;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DeleteHallListener {
    private final SeatService seatService;
    private final ReservationService reservationService;
    private final SessionService sessionService;

    @EventListener
    @Order(3)
    public void checkReservations(DeleteHallEvent deleteHallEvent) {
        validate(deleteHallEvent);
        List<ReservationDto> reservationsByHall = reservationService.getAllReservations(
                deleteHallEvent.getHallId(),
                null,
                null,
                null,
                null);
        if (!reservationsByHall.isEmpty()) {
            throw new IllegalArgumentException("Hall has reservations");
        }
    }

    @EventListener
    @Order(4)
    public void deleteSessions(DeleteHallEvent deleteHallEvent) {
        validate(deleteHallEvent);
        sessionService.deleteAllSessionsByHallId(deleteHallEvent.getHallId());
    }

    @EventListener
    @Order(5)
    public void deleteSeats(DeleteHallEvent deleteHallEvent) {
        validate(deleteHallEvent);
        seatService.deleteAllSeatsByHallId(deleteHallEvent.getHallId());
    }

    private void validate(DeleteHallEvent deleteHallEvent) {
        if (deleteHallEvent.getHallId() == null) {
            throw new IllegalArgumentException("Hall id is null");
        }
    }
}
