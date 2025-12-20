package org.example.cinemaservice.observer.event.hall;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.cinemaservice.observer.event.Stage;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Getter
public class HallEvent {
    private final Long hallId;
    private final LocalDateTime date;
    private final Stage stage;
}
