package org.example.cinemaservice.observer.listener.reservation;

import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.example.cinemaservice.dto.SessionDto;
import org.example.cinemaservice.model.Seat;
import org.example.cinemaservice.model.Session;
import org.example.cinemaservice.observer.event.reservation.SaveReservationEvent;
import org.example.cinemaservice.service.SessionService;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class SaveReservationListener {
    private final SessionService sessionService;

    @EventListener
    @Order(5)
    public void calculatePrice(SaveReservationEvent saveReservationEvent) {
        List<Seat> seatsByReservation = saveReservationEvent.getReservation().getSeats();
        if (CollectionUtils.isEmpty(seatsByReservation) || seatsByReservation.size() > 5) {
            throw new IllegalArgumentException("Seats count must be between 1 and 5");
        }

        Session sessionByReservation = saveReservationEvent.getReservation().getSession();
        if (sessionByReservation == null || sessionByReservation.getId() == null) {
            throw new IllegalArgumentException("Session id can not be null");
        }

        SessionDto session = sessionService.getSessionById(sessionByReservation.getId());
        int price = session.getPrice() * saveReservationEvent.getReservation().getSeats().size();
        saveReservationEvent.getReservation().setPrice(price);
    }
}
