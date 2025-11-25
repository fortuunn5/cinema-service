package org.example.cinemaservice.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.example.cinemaservice.dto.SessionDto;
import org.example.cinemaservice.model.Reservation;
import org.example.cinemaservice.model.Session;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

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
    public SessionDto readById(Long id) {
        return SessionDto.ofEntity(em.find(Session.class, id));
    }

    @Override
    public List<SessionDto> readAll() {
        TypedQuery<Session> query = em.createQuery("SELECT s FROM Session s", Session.class);
        List<SessionDto> sessionDtoList = new ArrayList<>();
        for (Session session : query.getResultList()) {
            sessionDtoList.add(SessionDto.ofEntity(session));
        }
        return sessionDtoList;
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
}
