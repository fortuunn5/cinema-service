package org.example.cinemaservice.observer.listener.movie;

import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.example.cinemaservice.dto.ReservationDto;
import org.example.cinemaservice.observer.event.movie.DeleteMovieEvent;
import org.example.cinemaservice.service.ReservationService;
import org.example.cinemaservice.service.SessionService;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DeleteMovieListener {
    private final ReservationService reservationService;
    private final SessionService sessionService;

    @EventListener
    @Order(5)
    public void checkReservations(DeleteMovieEvent deleteMovieEvent) {
        validate(deleteMovieEvent);
        List<ReservationDto> reservationsByMovie = reservationService.getAllReservations(
                null,
                null,
                deleteMovieEvent.getMovieId(),
                null,
                null);
        if (CollectionUtils.isNotEmpty(reservationsByMovie)) {
            throw new IllegalArgumentException("Movie has reservations");
        }
    }

    @EventListener
    @Order(6)
    public void deleteSessions(DeleteMovieEvent deleteMovieEvent) {
        validate(deleteMovieEvent);
        sessionService.deleteAllSessionsByMovieId(deleteMovieEvent.getMovieId());
    }

    private void validate(DeleteMovieEvent deleteMovieEvent) {
        if (deleteMovieEvent.getMovieId() == null) {
            throw new IllegalArgumentException("Movie id is null");
        }
    }
}
