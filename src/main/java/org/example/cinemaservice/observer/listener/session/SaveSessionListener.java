package org.example.cinemaservice.observer.listener.session;

import lombok.RequiredArgsConstructor;
import org.example.cinemaservice.dto.MovieDto;
import org.example.cinemaservice.model.Movie;
import org.example.cinemaservice.model.Session;
import org.example.cinemaservice.observer.event.session.SaveSessionEvent;
import org.example.cinemaservice.service.MovieService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Calendar;

@Component
@RequiredArgsConstructor
public class SaveSessionListener {
    @Value("${session.duration}")
    private Integer duration;
    private final MovieService movieService;

    @EventListener
    @Order(5)
    public void calculateDuration(SaveSessionEvent saveSessionEvent) {
        Movie emptyMovie = saveSessionEvent.getSession().getMovie();
        MovieDto movieBySession = movieService.getMovieById(emptyMovie.getId());
        if (movieBySession == null) {
            throw new IllegalArgumentException("Movie is null");
        }
        saveSessionEvent.getSession().setDuration(duration + movieBySession.getDuration());
    }

    @EventListener
    @Order(6)
    public void calculateEndDate(SaveSessionEvent saveSessionEvent) {
        Session session = saveSessionEvent.getSession();
        if (session.getStartDate() == null || session.getDuration() <= 0) {
            throw new IllegalArgumentException("Start date or duration is null");
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(session.getStartDate());
        calendar.add(Calendar.MINUTE, session.getDuration());
        session.setEndDate(calendar.getTime());
    }
}
