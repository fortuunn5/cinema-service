package org.example.cinemaservice.observer.listener.seat;

import lombok.RequiredArgsConstructor;
import org.example.cinemaservice.observer.event.seat.UpdateSeatEvent;
import org.example.cinemaservice.service.HallService;
import org.example.cinemaservice.service.SeatService;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UpdateSeatListener {
    private final SeatService seatService;
    private final HallService hallService;

    public void canBeUpdated(UpdateSeatEvent updateSeatEvent) {
        if (updateSeatEvent.getSeatId() == null) {
            throw new IllegalArgumentException("Seat id is null");
        }
        if (!(seatService.getAllSeatsByHallId(updateSeatEvent.getHallId()).size() < hallService.getHallById(updateSeatEvent.getHallId()).getCapacity())) {
            throw new IllegalArgumentException("Seat capacity exceeded");
        }
    }
}
