package org.example.cinemaservice.service;

import org.example.cinemaservice.dto.ReservationDto;
import org.example.cinemaservice.model.Reservation;

import java.util.List;

public interface ReservationService {
    ReservationDto createReservation(Reservation newReservation);

    ReservationDto createReservation(ReservationDto newReservation);

    ReservationDto getReservationById(Long id);

    List<ReservationDto> getAllReservations();

    List<ReservationDto> getAllReservationsByHallId(Long hallId);

    List<ReservationDto> getAllReservationsBySeatId(Long seatId);

    List<ReservationDto> getAllReservationsByMovieId(Long movieId);

    List<ReservationDto> getAllReservationsBySessionId(Long sessionId);

    ReservationDto updateReservation(Reservation upReservation);

    boolean deleteReservationById(Long id);

    boolean cancelReservationsByIds(List<Long> ids);

    boolean oneOfSeatsIsReserved(Long sessionId, List<Long> seatsId);
}
