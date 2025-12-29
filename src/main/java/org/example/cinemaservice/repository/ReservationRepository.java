package org.example.cinemaservice.repository;

import jakarta.annotation.Nullable;
import org.example.cinemaservice.dto.ReservationDto;
import org.example.cinemaservice.dto.UpdateReservationStatusDto;
import org.example.cinemaservice.model.Reservation;

import java.util.List;

public interface ReservationRepository {
    ReservationDto save(Reservation reservation);

    ReservationDto readById(Long id);

    List<ReservationDto> readAll(@Nullable Long hallId,
                                 @Nullable Long seatId,
                                 @Nullable Long movieId,
                                 @Nullable Long sessionId,
                                 @Nullable Long userId);

    ReservationDto update(Reservation reservation);

    boolean updateStatus(UpdateReservationStatusDto updateReservationStatus);

    boolean deleteById(Long id);

    int cancelByIds(List<Long> ids);

    boolean oneOfSeatsIsReserved(Long sessionId, List<Long> seatsId);
}
