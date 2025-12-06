package org.example.cinemaservice.model;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ReservationSeatKey implements Serializable {
    private Long reservationId;
    private Long seatId;

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof ReservationSeatKey that)) return false;
        return Objects.equals(reservationId, that.reservationId) && Objects.equals(seatId, that.seatId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(reservationId, seatId);
    }
}
