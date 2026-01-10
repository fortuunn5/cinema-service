package org.example.cinemaservice.repository;

import jakarta.annotation.Nullable;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.example.cinemaservice.dto.SessionDto;
import org.example.cinemaservice.model.Session;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class SessionRepositoryImpl implements SessionRepository {
    @PersistenceContext
    private final EntityManager em;

    @Override
    public SessionDto save(Session newSession) {
        em.persist(newSession);
        return SessionDto.ofEntity(newSession);
    }

    @Override
    public Optional<SessionDto> readById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Id cannot be null");
        }

        Session session = em.find(Session.class, id);
        if (session != null) {
            SessionDto sessionDto = SessionDto.ofEntity(session);
            return Optional.of(sessionDto);
        }
        return Optional.empty();
    }

    @Override
    public List<SessionDto> readAll(@Nullable Long movieId, @Nullable Date date, @Nullable Long hallId) {
        String readAll = "SELECT s FROM Session s WHERE 1=1";
        LocalDateTime startDate;
        LocalDateTime endDate;

        if (movieId != null) {
            readAll += " AND s.movie.id = :movieId";
        }
        if (date != null) {
            readAll += " AND s.startDate BETWEEN :startDate AND :endDate";
        }
        if (hallId != null) {
            readAll += " AND s.hall.id = :hallId";
        }

        TypedQuery<Session> query = em.createQuery(readAll, Session.class);

        if (movieId != null) {
            query.setParameter("movieId", movieId);
        }
        if (date != null) {
            //todo: перенести в DateUtils
            LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            startDate = LocalDateTime.of(localDate.getYear(), localDate.getMonth(), localDate.getDayOfMonth(), 0, 0, 0);
            endDate = LocalDateTime.of(localDate.getYear(), localDate.getMonth(), localDate.getDayOfMonth(), 23, 59, 59);
            query.setParameter("startDate", startDate);
            query.setParameter("endDate", endDate);
        }
        if (hallId != null) {
            query.setParameter("hallId", hallId);
        }

        List<Session> sessionList = query.getResultList();
        return sessionList.stream()
                .map(SessionDto::ofEntity)
                .collect(Collectors.toList());
    }

    @Override
    public SessionDto update(Session upSession) {
        return SessionDto.ofEntity(em.merge(upSession));
    }

    @Override
    public boolean deleteById(Long id) {
        Query query = em.createQuery("DELETE FROM Session s WHERE s.id = :id");
        query.setParameter("id", id);
        int count = query.executeUpdate();
        return count != 0;
    }

    @Override
    public int deleteAllByHallId(Long hallId) {
        Query query = em.createQuery("DELETE FROM Session s WHERE s.hall.id = :hallId");
        query.setParameter("hallId", hallId);
        return query.executeUpdate();
    }

    @Override
    public int deleteAllByMovieId(Long movieId) {
        Query query = em.createQuery("DELETE FROM Session s WHERE s.movie.id = :movieId");
        query.setParameter("movieId", movieId);
        return query.executeUpdate();
    }

    @Override
    public boolean hasConflictedTime(Long hallId, Date startDate, Date endDate, Long sessionId) {
        String queryString = """
                SELECT EXISTS(
                        SELECT 1
                        FROM Session s
                        WHERE s.hall.id=:hallId
                        AND NOT(
                            (:startDate<s.startDate AND :endDate<s.startDate)
                            OR (:startDate>s.endDate AND :endDate>s.endDate)
                            )
                """;
        if (sessionId != null) {
            queryString += " AND NOT s.id = :sessionId ";
        }
        queryString += ")";
        TypedQuery<Boolean> query = em.createQuery(queryString, Boolean.class);
        query.setParameter("hallId", hallId);
        query.setParameter("startDate", startDate);
        query.setParameter("endDate", endDate);
        if (sessionId != null) {
            query.setParameter("sessionId", sessionId);
        }
        return query.getSingleResult();
    }
}
