package org.example.cinemaservice.observer.event.session;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.cinemaservice.observer.event.Stage;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Getter
public class SessionEvent {
    private final Long sessionId;
    private final LocalDateTime date;
    private final Stage stage;
}
