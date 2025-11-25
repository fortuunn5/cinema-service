package org.example.cinemaservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.cinemaservice.model.Reservation;
import org.example.cinemaservice.model.Seat;
import org.example.cinemaservice.model.Session;
import org.example.cinemaservice.model.Status;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ReservationDto {
    private Long id;
    private String contactEmail;
    private Status status;
    private int price;
    private List<Long> seatsId;
    private Long sessionId;

    public Reservation toEntity() {
        Reservation reservation = new Reservation();
        reservation.setId(id);
        reservation.setContactEmail(contactEmail);
        reservation.setStatus(status);
        reservation.setPrice(price);
        List<Seat> seats = new ArrayList<>();
        if (seatsId != null) {
            for (Long id : seatsId) {
                seats.add(new Seat(id));
            }
        }
        reservation.setSeats(seats);
        reservation.setSession(new Session(sessionId));
        return reservation;
    }

    public static ReservationDto ofEntity(Reservation reservation) {
        if (reservation != null) {
            ReservationDto reservationDto = new ReservationDto();
            reservationDto.setId(reservation.getId());
            reservationDto.setContactEmail(reservation.getContactEmail());
            reservationDto.setStatus(reservation.getStatus());
            reservationDto.setPrice(reservation.getPrice());
            List<Long> seatsId = new ArrayList<>();
            for (Seat seat : reservation.getSeats()) {
                seatsId.add(seat.getId());
            }
            reservationDto.setSeatsId(seatsId);
            reservationDto.setSessionId(reservation.getSession().getId());
            return reservationDto;
        }
        return null;
    }
}
