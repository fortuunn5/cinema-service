package org.example.cinemaservice.observer.event.hall;

import lombok.Getter;
import org.example.cinemaservice.observer.event.Stage;

import java.time.LocalDateTime;

@Getter
public class DeleteHallEvent extends HallEvent {
    public DeleteHallEvent(Long hallId, LocalDateTime date) {
        super(hallId, date, Stage.DELETE);
    }
}
