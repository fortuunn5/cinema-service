package org.example.cinemaservice.observer.publisher;

import org.example.cinemaservice.observer.event.seat.SeatEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Component;

@Component
public class SeatPublisher implements ApplicationEventPublisherAware {
    private ApplicationEventPublisher publisher;

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.publisher = applicationEventPublisher;
    }

    public void publishEvent(SeatEvent seatEvent) {
        publisher.publishEvent(seatEvent);
    }
}
