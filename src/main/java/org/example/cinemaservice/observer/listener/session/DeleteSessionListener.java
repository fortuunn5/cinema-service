package org.example.cinemaservice.observer.listener.session;

import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.example.cinemaservice.dto.ReservationDto;
import org.example.cinemaservice.observer.event.session.DeleteSessionEvent;
import org.example.cinemaservice.service.ReservationService;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DeleteSessionListener {
    private final ReservationService reservationService;

    @EventListener
    @Order(5)
    public void checkReservations(DeleteSessionEvent deleteSessionEvent) {
        if (deleteSessionEvent.getSessionId() == null) {
            throw new IllegalArgumentException("Seat id is null");
        }

        List<ReservationDto> reservationsBySession = reservationService.getAllReservationsBySessionId(deleteSessionEvent.getSessionId());
        if (CollectionUtils.isNotEmpty(reservationsBySession)) {
            throw new IllegalArgumentException("Seat has reservations");
        }
    }
}
