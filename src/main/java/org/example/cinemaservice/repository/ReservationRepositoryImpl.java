package org.example.cinemaservice.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.example.cinemaservice.dto.ReservationDto;
import org.example.cinemaservice.model.Reservation;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ReservationRepositoryImpl implements ReservationRepository {
   @PersistenceContext
   private EntityManager em;

    @Override
    public ReservationDto save(Reservation newReservation) {
        em.persist(newReservation);
        return ReservationDto.ofEntity(newReservation);
    }

    @Override
    public ReservationDto readById(Long id) {
        return ReservationDto.ofEntity(em.find(Reservation.class, id));
    }

    @Override
    public List<ReservationDto> readAll() {
        TypedQuery<Reservation> query = em.createQuery("SELECT r FROM Reservation r", Reservation.class);
        List<ReservationDto> reservationDtoList = new ArrayList<>();
        for (Reservation reservation : query.getResultList()) {
            reservationDtoList.add(ReservationDto.ofEntity(reservation));
        }
        return reservationDtoList;
    }

    @Override
    public ReservationDto update(Reservation newReservation) {
        return ReservationDto.ofEntity(em.merge(newReservation));
    }

    @Override
    public boolean deleteById(Long id) {
        Query query = em.createQuery("DELETE FROM Reservation r WHERE r.id = :id");
        query.setParameter("id", id);
        int count = query.executeUpdate();
        return count != 0;
    }

    public int cancelByIds(List<Long> ids) {
        Query query = em.createQuery("DELETE FROM Reservation r WHERE r.id IN(:ids)");
        query.setParameter("ids", ids);
        return query.executeUpdate();
    }
}
