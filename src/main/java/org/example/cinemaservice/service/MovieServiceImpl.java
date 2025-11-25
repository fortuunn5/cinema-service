package org.example.cinemaservice.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.cinemaservice.dto.MovieDto;
import org.example.cinemaservice.model.Movie;
import org.example.cinemaservice.repository.MovieRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MovieServiceImpl implements MovieService {
    private final MovieRepository movieRepository;

    @Override
    public MovieDto createMovie(Movie newMovie) {
        if (newMovie.getId() != null) {
            throw new IllegalArgumentException("Movie id already exists");
        }
        return movieRepository.save(newMovie);
    }

    @Override
    public MovieDto getMovieById(Long id) {
        MovieDto movie = movieRepository.readById(id);
        if (movie == null) {
            throw new IllegalArgumentException("Movie not found");
        }
        return movie;
    }

    @Override
    public MovieDto getMovieByName(String name) {
        MovieDto movie = movieRepository.readByName(name);
        if (movie == null) {
            throw new IllegalArgumentException("Movie not found");
        }
        return movie;
    }

    @Override
    public List<MovieDto> getAllMovies() {
        return movieRepository.readAll();
    }

    @Override
    public MovieDto updateMovie(Movie upMovie) {
        if (upMovie.getId() == null || movieRepository.readById(upMovie.getId()) == null) {
            throw new IllegalArgumentException("Movie not found");
        }
        return movieRepository.update(upMovie);
    }

    @Override
    public boolean deleteMovieById(Long id) {
        if (movieRepository.deleteById(id)) {
            return true;
        }
        throw new IllegalArgumentException("Movie not found");
    }
}
