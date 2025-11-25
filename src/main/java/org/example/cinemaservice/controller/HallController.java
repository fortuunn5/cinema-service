package org.example.cinemaservice.controller;

import lombok.RequiredArgsConstructor;
import org.example.cinemaservice.dto.HallDto;
import org.example.cinemaservice.model.Hall;
import org.example.cinemaservice.service.HallService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/halls")
public class HallController {
    private final HallService hallService;

    @PostMapping
    public ResponseEntity<HallDto> createHall(@RequestBody HallDto newHall) {
        return new ResponseEntity<>(hallService.createHall(newHall.toEntity()), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HallDto> getHallById(@PathVariable(name = "id") Long id) {
        return new ResponseEntity<>(hallService.getHallById(id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<HallDto>> getAllHalls() {
        return new ResponseEntity<>(hallService.getAllHalls(), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<HallDto> updateHall(@RequestBody HallDto upHall) {
        return new ResponseEntity<>(hallService.updateHall(upHall.toEntity()), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHallById(@PathVariable(name = "id") Long id) {
        hallService.deleteHallById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
