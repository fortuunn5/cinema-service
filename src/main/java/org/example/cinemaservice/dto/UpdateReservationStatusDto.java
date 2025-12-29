package org.example.cinemaservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.cinemaservice.model.Status;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UpdateReservationStatusDto {
    private Long userId;
    private Long reservationId;
    private Status status;
}
