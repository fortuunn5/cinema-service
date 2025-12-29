package org.example.cinemaservice.service;

import jakarta.annotation.Nullable;
import org.example.cinemaservice.dto.UpdateReservationStatusDto;
import org.example.cinemaservice.dto.ReservationDto;
import org.example.cinemaservice.model.Reservation;

import java.util.List;

public interface ReservationService {
    ReservationDto createReservation(Reservation newReservation);

    ReservationDto createReservation(ReservationDto newReservation);

    ReservationDto getReservationById(Long id);

    List<ReservationDto> getAllReservations(@Nullable Long hallId,
                                            @Nullable Long seatId,
                                            @Nullable Long movieId,
                                            @Nullable Long sessionId,
                                            @Nullable Long userId);

    ReservationDto updateReservation(Reservation upReservation);

    boolean updateReservationStatus(UpdateReservationStatusDto changeReservationStatus);

    boolean deleteReservationById(Long id);

    boolean cancelReservationsByIds(List<Long> ids);

    boolean oneOfSeatsIsReserved(Long sessionId, List<Long> seatsId);
}
