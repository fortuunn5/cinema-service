package org.example.cinemaservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.cinemaservice.model.Hall;
import org.example.cinemaservice.model.Seat;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SeatDto {
    private Long id;
    private int row;
    private int number;
    private Long hallId;

    public Seat toEntity() {
        Seat seat = new Seat();
        seat.setId(id);
        seat.setRow(row);
        seat.setNumber(number);
        if (hallId != null) {
            Hall hall = new Hall();
            hall.setId(hallId);
            seat.setHall(hall);
        }
        return seat;
    }

    public static SeatDto ofEntity(Seat seat) {
        if (seat != null) {
            SeatDto seatDto = new SeatDto();
            seatDto.setId(seat.getId());
            seatDto.setRow(seat.getRow());
            seatDto.setNumber(seat.getNumber());
            seatDto.setHallId(HallDto.ofEntity(seat.getHall()).getId());
            return seatDto;
        }
        return null;
    }
}
