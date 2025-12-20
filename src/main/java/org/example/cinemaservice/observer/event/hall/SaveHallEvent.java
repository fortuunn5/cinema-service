package org.example.cinemaservice.observer.event.hall;

import lombok.Getter;
import org.example.cinemaservice.model.Hall;
import org.example.cinemaservice.observer.event.Stage;

import java.time.LocalDateTime;

@Getter
public class SaveHallEvent extends HallEvent {
    private final Hall hall;

    public SaveHallEvent(Hall hall, LocalDateTime date) {
        super(hall.getId(), date, Stage.SAVE);
        this.hall = hall;
    }
}
