package org.example.cinemaservice.controller;

import lombok.RequiredArgsConstructor;
import org.example.cinemaservice.dto.SessionDto;
import org.example.cinemaservice.model.Session;
import org.example.cinemaservice.service.SessionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/sessions")
public class SessionController {
    private final SessionService sessionService;

    @PostMapping
    public ResponseEntity<SessionDto> createSession(@RequestBody SessionDto newSession) {
        return new ResponseEntity<>(sessionService.createSession(newSession.toEntity()), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SessionDto> getSessionById(@PathVariable(name = "id") Long id) {
        return new ResponseEntity<>(sessionService.getSessionById(id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<SessionDto>> getAllSessions() {
        return new ResponseEntity<>(sessionService.getAllSessions(), HttpStatus.OK);
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
