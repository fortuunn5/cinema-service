package org.example.cinemaservice.observer;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Getter
public class HallEvent {
    private final Long hallId;
    private final LocalDateTime date;
    private final Stage stage;


}
