package org.example.cinemaservice.service;


import jakarta.annotation.Nullable;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.cinemaservice.dto.SessionDto;
import org.example.cinemaservice.model.Session;
import org.example.cinemaservice.observer.event.session.DeleteSessionEvent;
import org.example.cinemaservice.observer.event.session.SaveSessionEvent;
import org.example.cinemaservice.observer.publisher.SessionPublisher;
import org.example.cinemaservice.repository.SessionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class SessionServiceImpl implements SessionService {
    private final SessionRepository sessionRepository;
    private final SessionPublisher sessionPublisher;

    @Override
    public SessionDto createSession(Session newSession) {
        if (newSession.getId() != null) {
            throw new IllegalArgumentException("Session id already exists");
        }
        sessionPublisher.publishEvent(new SaveSessionEvent(newSession, LocalDateTime.now()));
        if (hasConflictedTime(newSession)) {
            throw new IllegalArgumentException("Session has conflicted time");
        }
        //todo: try catch
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
    public List<SessionDto> getSessions(@Nullable Long movieId, @Nullable Date date, @Nullable Long hallId) {
        return sessionRepository.readAll(movieId, date, hallId);
    }

    @Override
    public SessionDto updateSession(Session upSession) {
        if (upSession.getId() == null || sessionRepository.readById(upSession.getId()) == null) {
            throw new IllegalArgumentException("Session not found");
        }
        if (hasConflictedTime(upSession)) {
            throw new IllegalArgumentException("Session has conflicted time");
        }
        sessionPublisher.publishEvent(new SaveSessionEvent(upSession, LocalDateTime.now()));
        return sessionRepository.update(upSession);
    }

    @Override
    public boolean deleteSessionById(Long id) {
        sessionPublisher.publishEvent(new DeleteSessionEvent(id, LocalDateTime.now()));
        if (sessionRepository.deleteById(id)) {
            return true;
        }
        throw new IllegalArgumentException("Session not found");
    }

    @Override
    public int deleteAllSessionsByHallId(Long hallId) {
        List<SessionDto> sessionsByHall = getSessions(null, null, hallId);
        sessionsByHall.forEach(x -> deleteSessionById(x.getId()));
        return sessionsByHall.size();
    }

    @Override
    public int deleteAllSessionsByMovieId(Long movieId) {
        List<SessionDto> sessionsByMovie = getSessions(movieId, null, null);
        sessionsByMovie.forEach(x -> deleteSessionById(x.getId()));
        return sessionsByMovie.size();
    }

    private boolean hasConflictedTime(Session session) {
        return sessionRepository.hasConflictedTime(session.getHall().getId(), session.getStartDate(), session.getEndDate(), session.getId());
    }
}
