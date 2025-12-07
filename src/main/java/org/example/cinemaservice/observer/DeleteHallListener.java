package org.example.cinemaservice.observer;

import lombok.RequiredArgsConstructor;
import org.example.cinemaservice.service.SeatService;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeleteHallListener {
    private final SeatService seatService;

    @EventListener
    @Order(5)
    public void deleteSeats(DeleteHallEvent deleteHallEvent) {
        if (deleteHallEvent.getHallId() == null) {
            throw new IllegalArgumentException("Hall id is null");
        }
        seatService.deleteAllSeatsByHallId(deleteHallEvent.getHallId());
    }
}
