package org.example.cinemaservice.observer.event.session;

import org.example.cinemaservice.observer.event.Stage;

import java.time.LocalDateTime;

public class DeleteSessionEvent extends SessionEvent {
    public DeleteSessionEvent(Long sessionId, LocalDateTime date) {
        super(sessionId, date, Stage.DELETE);
    }
}
