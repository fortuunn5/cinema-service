package org.example.cinemaservice.repository;

import jakarta.annotation.Nullable;
import org.example.cinemaservice.dto.MovieDto;
import org.example.cinemaservice.model.Genre;
import org.example.cinemaservice.model.Movie;

import java.util.List;
import java.util.Optional;

public interface MovieRepository {
    MovieDto save(Movie newMovie);

    Optional<MovieDto> readById(Long id);

    List<MovieDto> readAll(@Nullable List<Genre> genres, @Nullable String name);

    MovieDto update(Movie upMovie);

    boolean deleteById(Long id);
}
