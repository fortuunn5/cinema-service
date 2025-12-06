package org.example.cinemaservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "reservation_seat")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ReservationSeat {
    @EmbeddedId
    ReservationSeatKey id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("reservationId")
    @JoinColumn(name = "reservation_id")
    Reservation reservation;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("seatId")
    @JoinColumn(name = "seats_id")
    Seat seat;
}
