package org.example.cinemaservice.service;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.cinemaservice.dto.SessionDto;
import org.example.cinemaservice.model.Session;
import org.example.cinemaservice.repository.SessionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class SessionServiceImpl implements SessionService {
    private final SessionRepository sessionRepository;

    @Override
    public SessionDto createSession(Session newSession) {
        if (newSession.getId() != null) {
            throw new IllegalArgumentException("Session id already exists");
        }
        return sessionRepository.save(newSession);
    }

    @Override
    public SessionDto getSessionById(Long id) {
        SessionDto session = sessionRepository.readById(id);
        if (session == null) {
            throw new IllegalArgumentException("Session not found");
        }
        return session;
    }

    @Override
    public List<SessionDto> getAllSessions() {
        return sessionRepository.readAll();
    }

    @Override
    public SessionDto updateSession(Session upSession) {
        if (upSession.getId() == null || sessionRepository.readById(upSession.getId()) == null) {
            throw new IllegalArgumentException("Session not found");
        }
        return sessionRepository.update(upSession);
    }

    @Override
    public boolean deleteSessionById(Long id) {
        if (sessionRepository.deleteById(id)) {
            return true;
        }
        throw new IllegalArgumentException("Session not found");
    }
}
