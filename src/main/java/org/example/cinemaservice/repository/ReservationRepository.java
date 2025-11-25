package org.example.cinemaservice.repository;

import org.example.cinemaservice.dto.ReservationDto;
import org.example.cinemaservice.model.Reservation;

import java.util.List;

public interface ReservationRepository {
    ReservationDto save(Reservation reservation);
    ReservationDto readById(Long id);
    List<ReservationDto> readAll();
    ReservationDto update(Reservation reservation);
    boolean deleteById(Long id);
}
