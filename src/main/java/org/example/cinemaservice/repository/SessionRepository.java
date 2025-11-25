package org.example.cinemaservice.repository;

import org.example.cinemaservice.dto.SessionDto;
import org.example.cinemaservice.model.Session;

import java.util.List;

public interface SessionRepository {
    SessionDto save(Session newSession);
    SessionDto readById(Long id);
    List<SessionDto> readAll();
    SessionDto update(Session upSession);
    boolean deleteById(Long id);
}
