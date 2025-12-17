package org.example.cinemaservice.observer.event.seat;

import org.example.cinemaservice.observer.event.Stage;

import java.time.LocalDateTime;

public class DeleteSeatEvent extends SeatEvent {
    public DeleteSeatEvent(Long seatId, LocalDateTime date) {
        super(seatId, date, Stage.DELETE);
    }
}
