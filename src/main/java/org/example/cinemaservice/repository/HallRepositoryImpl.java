package org.example.cinemaservice.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.example.cinemaservice.dto.HallDto;
import org.example.cinemaservice.model.Hall;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class HallRepositoryImpl implements HallRepository {
    @PersistenceContext
    private final EntityManager em;

    @Override
    public HallDto save(Hall newHall) {
        em.persist(newHall);
        return HallDto.ofEntity(newHall);
    }

    @Override
    public HallDto readById(Long id) {
        return HallDto.ofEntity(em.find(Hall.class, id));
    }

    @Override
    public List<HallDto> readAll() {
        TypedQuery<Hall> query = em.createQuery("SELECT h FROM Hall h", Hall.class);
        List<HallDto> hallDtoList = new ArrayList<>();
        List<Hall> resultList = query.getResultList();
        for (Hall hall : resultList) {
            hallDtoList.add(HallDto.ofEntity(hall));
        }
        return hallDtoList;
    }

    @Override
    public HallDto update(Hall upHall) {
        return HallDto.ofEntity(em.merge(upHall));
    }

    @Override
    public boolean deleteById(Long id) {
        Query deleteSeatsQuery = em.createQuery("DELETE FROM Seat s WHERE s.hall.id = :hallId");
        deleteSeatsQuery.setParameter("hallId", id);
        deleteSeatsQuery.executeUpdate();

        Query deleteHallQuery = em.createQuery("DELETE FROM Hall h WHERE h.id = :id");
        deleteHallQuery.setParameter("id", id);
        int count = deleteHallQuery.executeUpdate();

        return count != 0;
    }
}
