package org.example.cinemaservice.controller;

import lombok.RequiredArgsConstructor;
import org.example.cinemaservice.dto.MovieDto;
import org.example.cinemaservice.model.Movie;
import org.example.cinemaservice.repository.MovieRepository;
import org.example.cinemaservice.service.MovieService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/movies")
public class MovieController {
    private final MovieService movieService;

    @PostMapping
    public ResponseEntity<MovieDto> createMovie(@RequestBody MovieDto newMovie) {
        return new ResponseEntity<>(movieService.createMovie(newMovie.toEntity()), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovieDto> getMovieById(@PathVariable(name = "id") Long id) {
        return new ResponseEntity<>(movieService.getMovieById(id), HttpStatus.OK);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<MovieDto> getMovieByName(@PathVariable(name = "name") String name) {
        return new ResponseEntity<>(movieService.getMovieByName(name), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<MovieDto>> getAllMovies() {
        return new ResponseEntity<>(movieService.getAllMovies(), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<MovieDto> updateMovie(@RequestBody MovieDto upMovie) {
        return new ResponseEntity<>(movieService.updateMovie(upMovie.toEntity()), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMovieById(@PathVariable(name = "id") Long id) {
        movieService.deleteMovieById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
