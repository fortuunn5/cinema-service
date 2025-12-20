package org.example.cinemaservice.observer.event.seat;

import lombok.Getter;
import org.example.cinemaservice.observer.event.Stage;

import java.time.LocalDateTime;

@Getter
public class SaveSeatEvent extends SeatEvent {
    private final Long hallId;

    public SaveSeatEvent(Long seatId, LocalDateTime date, Long hallId) {
        super(seatId, date, Stage.SAVE);
        this.hallId = hallId;
    }
}
