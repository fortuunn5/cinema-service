package org.example.cinemaservice.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.cinemaservice.dto.ReservationDto;
import org.example.cinemaservice.model.Reservation;
import org.example.cinemaservice.model.Session;
import org.example.cinemaservice.repository.ReservationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ReservationServiceImpl implements ReservationService {
    private final ReservationRepository reservationRepository;
    private final SessionService sessionService;

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
    public ReservationDto createReservation(ReservationDto newReservation) {
        if (newReservation.getId() != null) {
            throw new IllegalArgumentException("Reservation id already exists");
        }
        Reservation r = newReservation.toEntity();
        Session s = sessionService.getSessionById(r.getSession().getId()).toEntity();
        r.setSession(s);
        if (r.getSeatsCount() < 1 || r.getSeatsCount() > 5) {
            throw new IllegalArgumentException("Seats count must be between 1 and 5");
        }
        return reservationRepository.save(r);
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
    public List<ReservationDto> getAllReservationsByHallId(Long hallId) {
        return reservationRepository.readAllByHallId(hallId);
    }

    public List<ReservationDto> getAllReservationsBySeatId(Long seatId) {
        return reservationRepository.readAllBySeatId(seatId);
    }

    @Override
    public List<ReservationDto> getAllReservationsByMovieId(Long movieId) {
        return reservationRepository.readAllByMovieId(movieId);
    }

    @Override
    public List<ReservationDto> getAllReservationsBySessionId(Long sessionId) {
        return reservationRepository.readAllBySessionId(sessionId);
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

    @Override
    public boolean cancelReservationsByIds(List<Long> ids) {
        int count = reservationRepository.cancelByIds(ids);
        if (count == ids.size()) {
            return true;
        }
        throw new IllegalArgumentException("Not all reservations are cancelled");
    }
}
