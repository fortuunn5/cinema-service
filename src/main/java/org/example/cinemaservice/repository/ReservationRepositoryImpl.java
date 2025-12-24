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
    public List<ReservationDto> readAllByHallId(Long hallId) {
        TypedQuery<Reservation> query = em.createQuery("SELECT r FROM Reservation r WHERE r.session.hall.id = :hallId", Reservation.class);
        query.setParameter("hallId", hallId);
        return query.getResultList().stream().map(ReservationDto::ofEntity).toList();
    }

    @Override
    public List<ReservationDto> readAllBySeatId(Long seatId) {
        TypedQuery<Reservation> query = em.createQuery("SELECT r FROM Reservation r WHERE r.id IN (SELECT rs.reservation.id FROM ReservationSeat rs WHERE rs.seat.id = :seatId)", Reservation.class);
        query.setParameter("seatId", seatId);
        return query.getResultList().stream().map(ReservationDto::ofEntity).toList();
    }

    @Override
    public List<ReservationDto> readAllByMovieId(Long movieId) {
        TypedQuery<Reservation> query = em.createQuery("SELECT r FROM Reservation r WHERE r.session.movie.id = :movieId", Reservation.class);
        query.setParameter("movieId", movieId);
        return query.getResultList().stream().map(ReservationDto::ofEntity).toList();
    }

    @Override
    public List<ReservationDto> readAllBySessionId(Long sessionId) {
        TypedQuery<Reservation> query = em.createQuery("SELECT r FROM Reservation r WHERE r.session.id = :sessionId", Reservation.class);
        query.setParameter("sessionId", sessionId);
        return query.getResultList().stream().map(ReservationDto::ofEntity).toList();
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

    @Override
    public boolean oneOfSeatsIsReserved(Long sessionId, List<Long> seatsId) {
        TypedQuery<Boolean> query = em.createQuery("SELECT EXISTS(SELECT 1 FROM Reservation r JOIN ReservationSeat rs ON r.id = rs.reservation.id WHERE r.session.id=:sessionId AND rs.seat.id IN (:seatsId))", Boolean.class);
        query.setParameter("sessionId", sessionId);
        query.setParameter("seatsId", seatsId);
        return query.getSingleResult();
    }
}
