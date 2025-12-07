package org.example.cinemaservice.observer;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class DeleteHallEvent extends HallEvent {
    public DeleteHallEvent(Long hallId, LocalDateTime date) {
        super(hallId, date, Stage.DELETE);
    }
}
