package org.example.cinemaservice.observer.listener.seat;

import lombok.RequiredArgsConstructor;
import org.example.cinemaservice.dto.SeatDto;
import org.example.cinemaservice.observer.event.seat.SaveSeatEvent;
import org.example.cinemaservice.service.HallService;
import org.example.cinemaservice.service.SeatService;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class SaveSeatListener {
    private final HallService hallService;
    private final SeatService seatService;

    @EventListener
    @Order(5)
    public void canBeSaved(SaveSeatEvent saveSeatEvent) {
        List<SeatDto> seatsByHall = seatService.getAllSeats(saveSeatEvent.getHallId());
        int hallCapacity = hallService.getHallById(saveSeatEvent.getHallId()).getCapacity();
        if (seatsByHall.size() >= hallCapacity) {
            throw new IllegalArgumentException("Hall capacity exceeded");
        }
    }
}
