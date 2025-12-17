package org.example.cinemaservice.observer.event.movie;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.cinemaservice.observer.event.Stage;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Getter
public class MovieEvent {
    private final Long movieId;
    private final LocalDateTime date;
    private final Stage stage;
}
