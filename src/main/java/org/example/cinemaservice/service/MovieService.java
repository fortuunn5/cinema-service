package org.example.cinemaservice.service;

import jakarta.annotation.Nullable;
import org.example.cinemaservice.dto.MovieDto;
import org.example.cinemaservice.model.Genre;
import org.example.cinemaservice.model.Movie;

import java.util.List;

public interface MovieService {
    MovieDto createMovie(Movie newMovie);

    MovieDto getMovieById(Long id);

    List<MovieDto> getMovies(@Nullable List<Genre> genres, @Nullable String name);

    MovieDto updateMovie(Movie upMovie);

    boolean deleteMovieById(Long id);
}
