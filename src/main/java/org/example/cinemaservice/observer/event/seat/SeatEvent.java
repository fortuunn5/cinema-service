package org.example.cinemaservice.observer.event.seat;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.cinemaservice.observer.event.Stage;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Getter
public class SeatEvent {
    private final Long seatId;
    private final LocalDateTime date;
    private final Stage stage;
}
