package org.example.cinemaservice.observer.event.reservation;

import lombok.Getter;
import org.example.cinemaservice.model.Reservation;
import org.example.cinemaservice.observer.event.Stage;

import java.time.LocalDateTime;

@Getter
public class SaveReservationEvent extends ReservationEvent {
    private final Reservation reservation;

    public SaveReservationEvent(Reservation reservation, LocalDateTime date) {
        super(reservation.getId(), date, Stage.SAVE);
        this.reservation = reservation;
    }
}
