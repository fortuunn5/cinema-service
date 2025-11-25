package org.example.cinemaservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.cinemaservice.model.DisplayFormat;
import org.example.cinemaservice.model.Hall;
import org.example.cinemaservice.model.Movie;
import org.example.cinemaservice.model.Session;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SessionDto {
    private Long id;
    private Date startDate;
    private int duration;
    private DisplayFormat displayFormat;
    private int price;
//    private HallDto hallDto;
//    private MovieDto movieDto;
    private Long hallId;
    private Long movieId;

    public Session toEntity() {
        Session session = new Session();
        session.setId(id);
        session.setStartDate(startDate);
        session.setDuration(duration);
        session.setDisplayFormat(displayFormat);
        session.setPrice(price);
        if (hallId != null) {
            Hall hall = new Hall();
            hall.setId(hallId);
            session.setHall(hall);
        }
        if (movieId != null) {
            Movie movie = new Movie();
            movie.setId(movieId);
            session.setMovie(movie);
        }
        return session;
    }

    public static SessionDto ofEntity(Session session) {
        if (session!=null) {
            SessionDto sessionDto = new SessionDto();
            sessionDto.setId(session.getId());
            sessionDto.setStartDate(session.getStartDate());
            sessionDto.setDuration(session.getDuration());
            sessionDto.setDisplayFormat(session.getDisplayFormat());
            sessionDto.setPrice(session.getPrice());
            sessionDto.setHallId(session.getHall().getId());
            sessionDto.setMovieId(session.getMovie().getId());
            return sessionDto;
        }
        return null;
    }
}
