package org.example.cinemaservice.repository;

import jakarta.annotation.Nullable;
import org.example.cinemaservice.dto.SessionDto;
import org.example.cinemaservice.model.Session;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface SessionRepository {
    SessionDto save(Session newSession);

    Optional<SessionDto> readById(Long id);

    List<SessionDto> readAll(@Nullable Long movieId, @Nullable Date date, @Nullable Long hallId);

    SessionDto update(Session upSession);

    boolean deleteById(Long id);

    int deleteAllByHallId(Long hallId);

    int deleteAllByMovieId(Long movieId);

    boolean hasConflictedTime(Long hallId, Date startDate, Date endDate, Long sessionId);
}
