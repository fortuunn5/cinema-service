package org.example.cinemaservice.service;

import org.example.cinemaservice.dto.ReservationDto;
import org.example.cinemaservice.model.Reservation;

import java.util.List;

public interface ReservationService {
    ReservationDto createReservation(Reservation newReservation);

    ReservationDto createReservation(ReservationDto newReservation);

    ReservationDto getReservationById(Long id);

    List<ReservationDto> getAllReservations();

    ReservationDto updateReservation(Reservation upReservation);

    boolean deleteReservationById(Long id);
}
