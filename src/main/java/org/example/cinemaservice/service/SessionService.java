package org.example.cinemaservice.service;

import jakarta.annotation.Nullable;
import org.example.cinemaservice.dto.SessionDto;
import org.example.cinemaservice.model.Session;

import java.util.Date;
import java.util.List;

public interface SessionService {
    SessionDto createSession(Session newSession);

    SessionDto getSessionById(Long id);

    List<SessionDto> getSessions(@Nullable Long movieId, @Nullable Date date, @Nullable Long hallId);

    SessionDto updateSession(Session upSession);

    boolean deleteSessionById(Long id);
}
