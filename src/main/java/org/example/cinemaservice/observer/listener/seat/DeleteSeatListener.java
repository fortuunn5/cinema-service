package org.example.cinemaservice.observer.listener.seat;

import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.example.cinemaservice.dto.ReservationDto;
import org.example.cinemaservice.observer.event.seat.DeleteSeatEvent;
import org.example.cinemaservice.service.ReservationService;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DeleteSeatListener {
    private final ReservationService reservationService;

    @EventListener
    @Order(5)
    public void checkReservations(DeleteSeatEvent deleteSeatEvent) {
        if (deleteSeatEvent.getSeatId() == null) {
            throw new IllegalArgumentException("Seat id is null");
        }

        List<ReservationDto> reservationsBySeat = reservationService.getAllReservationsBySeatId(deleteSeatEvent.getSeatId());
        if (CollectionUtils.isNotEmpty(reservationsBySeat)) {
            throw new IllegalArgumentException("Seat has reservations");
        }
    }
}
