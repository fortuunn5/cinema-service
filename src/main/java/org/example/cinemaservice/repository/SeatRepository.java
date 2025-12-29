package org.example.cinemaservice.repository;

import jakarta.annotation.Nullable;
import org.example.cinemaservice.dto.SeatDto;
import org.example.cinemaservice.dto.SeatWithIsReservedDto;
import org.example.cinemaservice.model.Seat;

import java.util.List;

public interface SeatRepository {
    SeatDto save(Seat newSeat);

    SeatDto readById(Long id);

    List<SeatDto> readAll(@Nullable Long hallId);

    SeatDto update(Seat upSeat);

    boolean deleteById(Long id);

    List<SeatWithIsReservedDto> readWithIsFreeBySessionId(Long sessionId);
}
