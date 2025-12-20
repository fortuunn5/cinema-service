package org.example.cinemaservice.observer.listener.hall;

import lombok.RequiredArgsConstructor;
import org.example.cinemaservice.model.Seat;
import org.example.cinemaservice.observer.event.hall.SaveHallEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class SaveHallListener {
    @EventListener
    @Order(5)
    public void canBeSaved(SaveHallEvent saveHallEvent) {
        List<Seat> seatsByHall = saveHallEvent.getHall().getSeats();
        int hallCapacity = saveHallEvent.getHall().getCapacity();
        if (seatsByHall.size() >= hallCapacity) {
            throw new IllegalArgumentException("Hall capacity exceeded");
        }
    }
}
