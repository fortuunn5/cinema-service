package org.example.cinemaservice.controller;

import lombok.RequiredArgsConstructor;
import org.example.cinemaservice.dto.SeatDto;
import org.example.cinemaservice.model.Seat;
import org.example.cinemaservice.service.SeatService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/seats")
public class SeatController {
    private final SeatService seatService;

    @PostMapping
    public ResponseEntity<SeatDto> createSeat(@RequestBody SeatDto newSeat) {
        return new ResponseEntity<>(seatService.createSeat(newSeat.toEntity()), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SeatDto> getSeatById(@PathVariable(name = "id") Long id) {
        return new ResponseEntity<>(seatService.getSeatById(id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<SeatDto>> getAllSeats() {
        return new ResponseEntity<>(seatService.getAllSeats(), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<SeatDto> updateSeat(@RequestBody SeatDto upSeat) {
        return new ResponseEntity<>(seatService.updateSeat(upSeat.toEntity()), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSeatById(@PathVariable(name = "id") Long id) {
        seatService.deleteSeatById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
