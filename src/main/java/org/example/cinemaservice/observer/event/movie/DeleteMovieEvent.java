package org.example.cinemaservice.observer.event.movie;

import org.example.cinemaservice.observer.event.Stage;

import java.time.LocalDateTime;

public class DeleteMovieEvent extends MovieEvent {
    public DeleteMovieEvent(Long movieId, LocalDateTime date) {
        super(movieId, date, Stage.DELETE);
    }
}
