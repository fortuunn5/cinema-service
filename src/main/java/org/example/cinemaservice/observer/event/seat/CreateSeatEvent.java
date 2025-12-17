package org.example.cinemaservice.observer.event.seat;

import lombok.Getter;
import org.example.cinemaservice.observer.event.Stage;

import java.time.LocalDateTime;

@Getter
public class CreateSeatEvent extends SeatEvent {
    private final Long hallId;

    public CreateSeatEvent(Long seatId, LocalDateTime date, Long hallId) {
        super(seatId, date, Stage.SAVE);
        this.hallId = hallId;
    }
}
