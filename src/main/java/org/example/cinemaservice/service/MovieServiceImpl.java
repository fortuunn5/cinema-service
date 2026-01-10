package org.example.cinemaservice.service;

import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import org.example.cinemaservice.dto.MovieDto;
import org.example.cinemaservice.model.Genre;
import org.example.cinemaservice.model.Movie;
import org.example.cinemaservice.observer.event.movie.DeleteMovieEvent;
import org.example.cinemaservice.observer.publisher.MoviePublisher;
import org.example.cinemaservice.repository.MovieRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class MovieServiceImpl implements MovieService {
    private final MovieRepository movieRepository;
    private final MoviePublisher moviePublisher;

    @Override
    public MovieDto createMovie(Movie newMovie) {
        if (newMovie.getId() != null) {
            throw new IllegalArgumentException("Movie id already exists");
        }
        return movieRepository.save(newMovie);
    }

    @Override
    public MovieDto getMovieById(Long id) {
        Optional<MovieDto> movieDto = movieRepository.readById(id);
        return movieDto.orElseThrow(() -> new IllegalArgumentException("Movie with id " + id + " not found"));
    }

    @Override
    public List<MovieDto> getMovies(@Nullable List<Genre> genres, @Nullable String name) {
        return movieRepository.readAll(genres, name);
    }

    @Override
    public MovieDto updateMovie(Movie upMovie) {
        MovieDto movieDto = getMovieById(upMovie.getId());

        Movie movie = movieDto.toEntity();
        if (upMovie.getName() != null) {
            movie.setName(upMovie.getName());
        }
        if (upMovie.getDuration() != null) {
            movie.setDuration(upMovie.getDuration());
        }
        if (upMovie.getDescription() != null) {
            movie.setDescription(upMovie.getDescription());
        }

        return movieRepository.update(movie);
    }

    @Override
    public boolean deleteMovieById(Long id) {
        moviePublisher.publishEvent(new DeleteMovieEvent(id, LocalDateTime.now()));
        if (movieRepository.deleteById(id)) {
            return true;
        }
        throw new IllegalArgumentException("Movie with id " + id + " not found");
    }
}
