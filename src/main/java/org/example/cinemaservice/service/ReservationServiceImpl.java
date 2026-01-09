package org.example.cinemaservice.service;

import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import org.example.cinemaservice.dto.ReservationDto;
import org.example.cinemaservice.dto.UpdateReservationStatusDto;
import org.example.cinemaservice.dto.UserDto;
import org.example.cinemaservice.model.Reservation;
import org.example.cinemaservice.model.Session;
import org.example.cinemaservice.observer.event.reservation.SaveReservationEvent;
import org.example.cinemaservice.observer.publisher.ReservationPublisher;
import org.example.cinemaservice.repository.ReservationRepository;
import org.example.cinemaservice.utils.SecurityUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ReservationServiceImpl implements ReservationService {
    private final ReservationRepository reservationRepository;
    private final SessionService sessionService;
    private final ReservationPublisher reservationPublisher;
    private final UserService userService;

    @Override
    public ReservationDto createReservation(ReservationDto newReservationDto) {
        Reservation newReservation = newReservationDto.toEntity();
        UserDto user;
        if (SecurityUtils.hasRole("ROLE_ADMIN") && newReservation.getUser().getId() != null) {
            user = userService.getUserById(newReservation.getUser().getId());
        } else {
            user = userService.getUserByContactEmail(SecurityUtils.getCurrentUserEmail());
        }
        newReservation.setUser(user.toEntity());

        if (newReservationDto.getId() != null) {
            throw new IllegalArgumentException("Reservation id already exists");
        }

        Session s = sessionService.getSessionById(newReservation.getSession().getId()).toEntity();
        newReservation.setSession(s);
        reservationPublisher.publishEvent(new SaveReservationEvent(newReservation, LocalDateTime.now()));

        return reservationRepository.save(newReservation);
    }

    @Override
    public ReservationDto getReservationById(Long id) {
        Optional<ReservationDto> reservationDto = reservationRepository.readById(id);
        if (reservationDto.isEmpty()) {
            throw new IllegalArgumentException("Reservation with id " + id + " not found");
        }

        ReservationDto reservation = reservationDto.get();
        UserDto userByReservation = userService.getUserById(reservation.getUserId());
        UserDto currentUser = userService.getUserByContactEmail(SecurityUtils.getCurrentUserEmail());

        if (SecurityUtils.hasRole("ROLE_ADMIN") || userByReservation.getId().equals(currentUser.getId())) {
            return reservation;
        }
        throw new RuntimeException("Not enough rights");

    }

    @Override
    public List<ReservationDto> getAllReservations(@Nullable Long hallId,
                                                   @Nullable Long seatId,
                                                   @Nullable Long movieId,
                                                   @Nullable Long sessionId,
                                                   @Nullable Long userId) {
        if (userId != null && SecurityUtils.hasRole("ROLE_ADMIN")) {
            return reservationRepository.readAll(hallId, seatId, movieId, sessionId, userId);
        }
        UserDto user = userService.getUserByContactEmail(SecurityUtils.getCurrentUserEmail());
        return reservationRepository.readAll(hallId, seatId, movieId, sessionId, user.getId());
    }

    @Override
    public ReservationDto updateReservation(Reservation upReservation) {
        Reservation reservation = getReservationById(upReservation.getId()).toEntity();

        reservationPublisher.publishEvent(new SaveReservationEvent(reservation, LocalDateTime.now()));
        return reservationRepository.update(upReservation);
    }

    @Override
    public boolean updateReservationStatus(UpdateReservationStatusDto updateReservationStatusDto) {
        UserDto currentUser = userService.getUserByContactEmail(SecurityUtils.getCurrentUserEmail());
        if(!SecurityUtils.hasRole("ROLE_ADMIN") && !currentUser.getId().equals(updateReservationStatusDto.getUserId())){
            throw new RuntimeException("Not enough rights");
        }

        if (reservationRepository.updateStatus(updateReservationStatusDto)) {
            return true;
        }
        throw new IllegalArgumentException("Reservation not found");
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

    @Override
    public boolean oneOfSeatsIsReserved(Long sessionId, List<Long> seatsId) {
        return reservationRepository.oneOfSeatsIsReserved(sessionId, seatsId);
    }
}
