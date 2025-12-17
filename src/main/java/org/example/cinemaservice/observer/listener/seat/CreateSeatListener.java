package org.example.cinemaservice.observer.listener.seat;

import org.example.cinemaservice.observer.event.seat.CreateSeatEvent;
import org.example.cinemaservice.service.HallService;
import org.example.cinemaservice.service.SeatService;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
public class CreateSeatListener {
    private HallService hallService;
    private SeatService seatService;

    @EventListener
    @Order(5)
    public void canBeCreated(CreateSeatEvent createSeatEvent) {
        if (createSeatEvent.getSeatId() == null) {
            throw new IllegalArgumentException("Seat id is null");
        }
        if (!(seatService.getAllSeatsByHallId(createSeatEvent.getHallId()).size() < hallService.getHallById(createSeatEvent.getHallId()).getCapacity())) {
            throw new IllegalArgumentException("Seat capacity exceeded");
        }
    }
}
