package org.example.cinemaservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.cinemaservice.model.Reservation;
import org.example.cinemaservice.model.Seat;
import org.example.cinemaservice.model.Session;
import org.example.cinemaservice.model.Status;
import org.example.cinemaservice.model.User;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ReservationDto {
    private Long id;
    private Status status;
    private Integer price;
    private List<Long> seatsId;
    private Long sessionId;
    private Long userId;

    public Reservation toEntity() {
        Reservation reservation = new Reservation();
        reservation.setId(id);
        reservation.setStatus(status);
        reservation.setPrice(price);
        List<Seat> seats = new ArrayList<>();
        if (seatsId != null) {
            for (Long id : seatsId) {
                Seat seat = new Seat(id);
                seats.add(seat);
            }
        }
        reservation.setSeats(seats);
        Session session = new Session(sessionId);
        reservation.setSession(session);
        User user = new User(userId);
        reservation.setUser(user);
        return reservation;
    }

    public static ReservationDto ofEntity(Reservation reservation) {
        if (reservation != null) {
            ReservationDto reservationDto = new ReservationDto();
            reservationDto.setId(reservation.getId());
            reservationDto.setStatus(reservation.getStatus());
            reservationDto.setPrice(reservation.getPrice());
            List<Long> seatsId = new ArrayList<>();
            for (Seat seat : reservation.getSeats()) {
                seatsId.add(seat.getId());
            }
            reservationDto.setSeatsId(seatsId);
            reservationDto.setSessionId(reservation.getSession().getId());
            reservationDto.setUserId(reservation.getUser().getId());
            return reservationDto;
        }
        return null;
    }
}
