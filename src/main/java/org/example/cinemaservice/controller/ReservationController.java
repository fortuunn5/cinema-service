package org.example.cinemaservice.controller;

import lombok.RequiredArgsConstructor;
import org.example.cinemaservice.dto.ReservationDto;
import org.example.cinemaservice.dto.UpdateReservationStatusDto;
import org.example.cinemaservice.service.ReservationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reservations")
public class ReservationController {
    private final ReservationService reservationService;

    @PostMapping
    public ResponseEntity<ReservationDto> createReservation(@RequestBody ReservationDto newReservation) {
        return new ResponseEntity<>(reservationService.createReservation(newReservation), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservationDto> getReservationById(@PathVariable(name = "id") Long id) {
        return new ResponseEntity<>(reservationService.getReservationById(id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<ReservationDto>> getAllReservations(@RequestParam(name = "hallId", required = false) Long hallId,
                                                                   @RequestParam(name = "seatId", required = false) Long seatId,
                                                                   @RequestParam(name = "movieId", required = false) Long movieId,
                                                                   @RequestParam(name = "sessionId", required = false) Long sessionId,
                                                                   @RequestParam(name = "userId", required = false) Long userId) {
        return new ResponseEntity<>(reservationService.getAllReservations(hallId, seatId, movieId, sessionId, userId), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<ReservationDto> updateReservation(@RequestBody ReservationDto upReservation) {
        return new ResponseEntity<>(reservationService.updateReservation(upReservation.toEntity()), HttpStatus.OK);
    }

    @PutMapping("/changeStatus")
    public ResponseEntity<Void> updateReservationStatus(@RequestBody UpdateReservationStatusDto updateReservationStatusDto) {
        reservationService.updateReservationStatus(updateReservationStatusDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservationById(@PathVariable(name = "id") Long id) {
        reservationService.deleteReservationById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
