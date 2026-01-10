package org.example.cinemaservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.cinemaservice.model.Hall;
import org.example.cinemaservice.model.Seat;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class HallDto {
    private Long id;
    private String name;
    private Integer capacity;
    private List<Long> seatsId;

    public Hall toEntity() {
        Hall hall = new Hall();
        hall.setId(id);
        hall.setName(name);
        hall.setCapacity(capacity);
        List<Seat> seats = new ArrayList<>();
        if (seatsId != null) {
            for (Long seatId : seatsId) {
                seats.add(new Seat(seatId));
            }
        }
        hall.setSeats(seats);
        return hall;
    }

    public static HallDto ofEntity(Hall hall) {
        if (hall!=null) {
            HallDto hallDto = new HallDto();
            hallDto.setId(hall.getId());
            hallDto.setName(hall.getName());
            hallDto.setCapacity(hall.getCapacity());
            List<Long> seatsDto = new ArrayList<>();
            if (hall.getSeats() != null) {
                for (Seat seat : hall.getSeats()) {
                    seatsDto.add(seat.getId());
                }
            }
            hallDto.setSeatsId(seatsDto);
            return hallDto;
        }
        return null;
    }
}
