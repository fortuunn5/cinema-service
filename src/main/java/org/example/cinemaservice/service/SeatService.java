package org.example.cinemaservice.service;

import org.example.cinemaservice.dto.SeatDto;
import org.example.cinemaservice.dto.SeatWithIsReservedDto;
import org.example.cinemaservice.model.Seat;

import java.util.List;

public interface SeatService {
    SeatDto createSeat(Seat newSeat);

    SeatDto getSeatById(Long id);

    List<SeatDto> getAllSeats();

    SeatDto updateSeat(Seat upSeat);

    boolean deleteSeatById(Long id);

    List<SeatWithIsReservedDto> getSeatsWithIsFreeBySessionId(Long sessionId);
}
