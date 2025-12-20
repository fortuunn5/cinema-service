package org.example.cinemaservice.observer.event.session;

import lombok.Getter;
import org.example.cinemaservice.model.Session;
import org.example.cinemaservice.observer.event.Stage;

import java.time.LocalDateTime;

@Getter
public class SaveSessionEvent extends SessionEvent {
    private final Session session;

    public SaveSessionEvent(Session session, LocalDateTime date) {
        super(session.getId(), date, Stage.SAVE);
        this.session = session;
    }
}
