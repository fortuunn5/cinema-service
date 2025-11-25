package org.example.cinemaservice.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.cinemaservice.dto.ReservationDto;
import org.example.cinemaservice.model.Reservation;
import org.example.cinemaservice.repository.ReservationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ReservationServiceImpl implements ReservationService {
    private final ReservationRepository reservationRepository;

    @Override
    public ReservationDto createReservation(Reservation newReservation) {
        if (newReservation.getId() != null) {
            throw new IllegalArgumentException("Reservation id already exists");
        }
        //todo: проверить
        if (newReservation.getSeatsCount() < 1 || newReservation.getSeatsCount() > 5) {
            throw new IllegalArgumentException("Seats count must be between 1 and 5");
        }
        return reservationRepository.save(newReservation);
    }

    @Override
    public ReservationDto getReservationById(Long id) {
        ReservationDto reservation = reservationRepository.readById(id);
        if (reservation == null) {
            throw new IllegalArgumentException("Reservation not found");
        }
        return reservation;
    }

    @Override
    public List<ReservationDto> getAllReservations() {
        return reservationRepository.readAll();
    }

    @Override
    public ReservationDto updateReservation(Reservation upReservation) {
        if (upReservation.getId() == null || reservationRepository.readById(upReservation.getId()) == null) {
            throw new IllegalArgumentException("Reservation not found");
        }
        return reservationRepository.update(upReservation);
    }

    @Override
    public boolean deleteReservationById(Long id) {
        if (reservationRepository.deleteById(id)) {
            return true;
        }
        throw new IllegalArgumentException("Reservation not found");
    }
}
