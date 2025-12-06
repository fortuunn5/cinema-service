package org.example.cinemaservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SeatWithIsReservedDto {
    private Long sessionId;
    private Long hallId;

    private Long seatId;
    private int row;
    private int number;
    private Boolean isFree;
}
