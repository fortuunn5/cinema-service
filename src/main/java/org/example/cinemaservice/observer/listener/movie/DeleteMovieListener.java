package org.example.cinemaservice.observer.listener.movie;

import lombok.RequiredArgsConstructor;
import org.example.cinemaservice.observer.event.movie.DeleteMovieEvent;
import org.example.cinemaservice.service.ReservationService;
import org.example.cinemaservice.service.SessionService;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeleteMovieListener {
    private final ReservationService reservationService;
    private final SessionService sessionService;

    @EventListener
    @Order(5)
    public void checkReservations(DeleteMovieEvent deleteMovieEvent) {
        if (deleteMovieEvent.getMovieId() == null) {
            throw new IllegalArgumentException("Movie id is null");
        }
        if (!reservationService.getAllReservationsByMovieId(deleteMovieEvent.getMovieId()).isEmpty()) {
            throw new IllegalArgumentException("Movie has reservations");
        }
    }

    @EventListener
    @Order(6)
    public void deleteSessions(DeleteMovieEvent deleteMovieEvent) {
        if (deleteMovieEvent.getMovieId() == null) {
            throw new IllegalArgumentException("Movie id is null");
        }
        sessionService.deleteAllSessionsByMovieId(deleteMovieEvent.getMovieId());
    }
}
