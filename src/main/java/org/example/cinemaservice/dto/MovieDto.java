package org.example.cinemaservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.cinemaservice.model.Genre;
import org.example.cinemaservice.model.Movie;
import org.example.cinemaservice.model.Session;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MovieDto {
    private Long id;
    private String name;
    private List<Genre> genres;
    private int duration;
    private String description;
    private List<Long> sessionsId;

    public Movie toEntity() {
        Movie movie = new Movie();
        movie.setId(id);
        movie.setName(name);
        movie.setGenres(genres);
        movie.setDuration(duration);
        movie.setDescription(description);
        List<Session> sessions = new ArrayList<>();
        if(sessionsId != null) {
            for (Long sessionId : sessionsId) {
                sessions.add(new Session(sessionId));
            }
        }
        movie.setSessions(sessions);
        return movie;
    }

    public static MovieDto ofEntity(Movie movie) {
        if (movie != null) {
            MovieDto movieDto = new MovieDto();
            movieDto.setId(movie.getId());
            movieDto.setName(movie.getName());
            movieDto.setGenres(movie.getGenres());
            movieDto.setDuration(movie.getDuration());
            movieDto.setDescription(movie.getDescription());
            List<Long> sessionsId = new ArrayList<>();
            if (movie.getSessions() != null) {
                for (Session session : movie.getSessions()) {
                    sessionsId.add(session.getId());
                }
            }
            movieDto.setSessionsId(sessionsId);
            return movieDto;
        }
        return null;
    }
}
