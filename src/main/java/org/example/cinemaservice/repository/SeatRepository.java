package org.example.cinemaservice.repository;

import org.example.cinemaservice.dto.SeatDto;
import org.example.cinemaservice.model.Seat;

import java.util.List;

public interface SeatRepository {
    SeatDto save(Seat newSeat);
    SeatDto readById(Long id);
    List<SeatDto> readAll();
    SeatDto update(Seat upSeat);
    boolean deleteById(Long id);
}
