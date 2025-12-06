package org.example.cinemaservice.controller;

import lombok.RequiredArgsConstructor;
import org.example.cinemaservice.dto.SeatWithIsReservedDto;
import org.example.cinemaservice.dto.SessionDto;
import org.example.cinemaservice.service.SeatService;
import org.example.cinemaservice.service.SessionService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/sessions")
public class SessionController {
    private final SessionService sessionService;
    private final SeatService seatService;

    @PostMapping
    public ResponseEntity<SessionDto> createSession(@RequestBody SessionDto newSession) {
        return new ResponseEntity<>(sessionService.createSession(newSession.toEntity()), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SessionDto> getSessionById(@PathVariable(name = "id") Long id) {
        return new ResponseEntity<>(sessionService.getSessionById(id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<SessionDto>> getAllSessions(@RequestParam(name = "movieId", required = false)
                                                           Long movieId,
                                                           @RequestParam(name = "date", required = false)
                                                           @DateTimeFormat(pattern = "dd.MM.yyyy")
                                                           Date date,
                                                           @RequestParam(name = "hallId", required = false)
                                                           Long hallId) {
        return new ResponseEntity<>(sessionService.getSessions(movieId, date, hallId), HttpStatus.OK);
    }

    @GetMapping("/{id}/seats")
    public ResponseEntity<List<SeatWithIsReservedDto>> getAllSeatsWithIsFreeBySessionId(@PathVariable Long id) {
        return new ResponseEntity<>(seatService.getSeatsWithIsFreeBySessionId(id), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<SessionDto> updateSession(@RequestBody SessionDto upSession) {
        return new ResponseEntity<>(sessionService.updateSession(upSession.toEntity()), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSessionById(@PathVariable(name = "id") Long id) {
        sessionService.deleteSessionById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
