package org.example.cinemaservice.service;

import org.example.cinemaservice.dto.SessionDto;
import org.example.cinemaservice.model.Session;

import java.util.List;

public interface SessionService {
    SessionDto createSession(Session newSession);
    SessionDto getSessionById(Long id);
    List<SessionDto> getAllSessions();
    SessionDto updateSession(Session upSession);
    boolean deleteSessionById(Long id);
}
