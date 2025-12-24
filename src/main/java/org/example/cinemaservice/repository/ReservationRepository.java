package org.example.cinemaservice.repository;

import org.example.cinemaservice.dto.ReservationDto;
import org.example.cinemaservice.model.Reservation;

import java.util.List;

public interface ReservationRepository {
    ReservationDto save(Reservation reservation);

    ReservationDto readById(Long id);

    List<ReservationDto> readAll();

    List<ReservationDto> readAllByHallId(Long hallId);

    List<ReservationDto> readAllBySeatId(Long seatId);

    List<ReservationDto> readAllByMovieId(Long movieId);

    List<ReservationDto> readAllBySessionId(Long sessionId);

    ReservationDto update(Reservation reservation);

    boolean deleteById(Long id);

    int cancelByIds(List<Long> ids);

    boolean oneOfSeatsIsReserved(Long sessionId, List<Long> seatsId);
}
