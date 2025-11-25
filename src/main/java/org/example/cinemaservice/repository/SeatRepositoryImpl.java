package org.example.cinemaservice.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.example.cinemaservice.dto.SeatDto;
import org.example.cinemaservice.model.Reservation;
import org.example.cinemaservice.model.Seat;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class SeatRepositoryImpl implements SeatRepository {
    @PersistenceContext
    private final EntityManager em;

    @Override
    public SeatDto save(Seat newSeat) {
        em.persist(newSeat);
        return SeatDto.ofEntity(newSeat);
    }

    @Override
    public SeatDto readById(Long id) {
        return SeatDto.ofEntity(em.find(Seat.class, id));
    }

    @Override
    public List<SeatDto> readAll() {
        TypedQuery<Seat> query = em.createQuery("SELECT s FROM Seat s ", Seat.class);
        List<SeatDto> seatDtoList = new ArrayList<>();
        for (Seat seat : query.getResultList()) {
            seatDtoList.add(SeatDto.ofEntity(seat));
        }
        return seatDtoList;
    }

    @Override
    public SeatDto update(Seat upSeat) {
        return SeatDto.ofEntity(em.merge(upSeat));
    }

    @Override
    public boolean deleteById(Long id) {
        Query query = em.createQuery("DELETE FROM Seat s WHERE s.id = :id");
        query.setParameter("id", id);
        int count = query.executeUpdate();
        return count != 0;
    }
}
