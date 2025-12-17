package org.example.cinemaservice.observer.publisher;

import org.example.cinemaservice.observer.event.session.SessionEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Component;

@Component
public class SessionPublisher implements ApplicationEventPublisherAware {
    private ApplicationEventPublisher publisher;

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.publisher = applicationEventPublisher;
    }

    public void publishEvent(SessionEvent sessionEvent) {
        publisher.publishEvent(sessionEvent);
    }
}
