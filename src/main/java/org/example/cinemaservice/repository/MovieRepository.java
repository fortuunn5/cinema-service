package org.example.cinemaservice.repository;

import org.example.cinemaservice.dto.MovieDto;
import org.example.cinemaservice.model.Movie;

import java.util.List;

public interface MovieRepository {
    MovieDto save(Movie newMovie);
    MovieDto readById(Long id);
    MovieDto readByName(String name);
    List<MovieDto> readAll();
    MovieDto update(Movie upMovie);
    boolean deleteById(Long id);
}
