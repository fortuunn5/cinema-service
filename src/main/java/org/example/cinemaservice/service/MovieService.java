package org.example.cinemaservice.service;

import org.example.cinemaservice.dto.MovieDto;
import org.example.cinemaservice.model.Movie;

import java.util.List;

public interface MovieService {
    MovieDto createMovie(Movie newMovie);
    MovieDto getMovieById(Long id);
    MovieDto getMovieByName(String name);
    List<MovieDto> getAllMovies();
    MovieDto updateMovie(Movie upMovie);
    boolean deleteMovieById(Long id);
}
