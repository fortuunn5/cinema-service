package org.example.cinemaservice.observer;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class SaveHallEvent extends HallEvent {
    public SaveHallEvent(Long hallId, LocalDateTime date, Stage stage) {
        super(hallId, date, Stage.SAVE);
    }
}
