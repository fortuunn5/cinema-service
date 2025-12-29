package org.example.cinemaservice.service;

import jakarta.annotation.Nullable;
import jakarta.transaction.Transactional;
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

import java.time.LocalDateTime;
import java.util.List;
//todo: переделать
@Service
@RequiredArgsConstructor
@Transactional
public class ReservationServiceImpl implements ReservationService {
    private final ReservationRepository reservationRepository;
    private final SessionService sessionService;
    private final ReservationPublisher reservationPublisher;
    private final RoleService roleService;
    private final UserService userService;

    @Override
    public ReservationDto createReservation(Reservation newReservation) {
        if (SecurityUtils.getCurrentRole(userService, roleService).getRole().equals("ROLE_USER") && !SecurityUtils.getCurrentUserId(userService).equals(newReservation.getUser().getId())) {
            throw new IllegalArgumentException("Not enough rights");
        }
        if (newReservation.getId() != null) {
            throw new IllegalArgumentException("Reservation id already exists");
        }
        reservationPublisher.publishEvent(new SaveReservationEvent(newReservation, LocalDateTime.now()));

        return reservationRepository.save(newReservation);
    }

    @Override
    public ReservationDto createReservation(ReservationDto newReservation) {
        if (SecurityUtils.getCurrentRole(userService, roleService).getRole().equals("ROLE_USER") && !SecurityUtils.getCurrentUserId(userService).equals(newReservation.getUserId())) {
            throw new IllegalArgumentException("Not enough rights");
        }
        if (newReservation.getId() != null) {
            throw new IllegalArgumentException("Reservation id already exists");
        }
        Reservation r = newReservation.toEntity();
        Session s = sessionService.getSessionById(r.getSession().getId()).toEntity();
        r.setSession(s);
        reservationPublisher.publishEvent(new SaveReservationEvent(r, LocalDateTime.now()));
        return reservationRepository.save(r);
    }

    @Override
    public ReservationDto getReservationById(Long id) {
        ReservationDto reservation = reservationRepository.readById(id);
        if (reservation != null) {
            if (SecurityUtils.getCurrentRole(userService, roleService).getRole().equals("ROLE_USER") && !SecurityUtils.getCurrentUserId(userService).equals(reservation.getUserId())) {
                throw new IllegalArgumentException("Not enough rights");
            }
            return reservation;
        }
        throw new IllegalArgumentException("Reservation not found");
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
        UserDto user = userService.getUserByEmail(SecurityUtils.getCurrentUserEmail());
        return reservationRepository.readAll(hallId, seatId, movieId, sessionId, user.getId());
    }

    @Override
    public ReservationDto updateReservation(Reservation upReservation) {
//        todo check
//        if (upReservation.getId() == null || reservationRepository.readById(upReservation.getId()) == null) {
//            throw new IllegalArgumentException("Reservation not found");
//        }
        reservationPublisher.publishEvent(new SaveReservationEvent(upReservation, LocalDateTime.now()));
        return reservationRepository.update(upReservation);
    }

    @Override
    public boolean updateReservationStatus(UpdateReservationStatusDto updateReservationStatusDto) {
        if (SecurityUtils.getCurrentRole(userService, this.roleService).getRole().equals("ROLE_USER") && !SecurityUtils.getCurrentUserId(userService).equals(updateReservationStatusDto.getUserId())) {
            throw new IllegalArgumentException("Not enough rights");
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
