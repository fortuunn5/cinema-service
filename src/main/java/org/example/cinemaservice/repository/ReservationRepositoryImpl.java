package org.example.cinemaservice.repository;

import jakarta.annotation.Nullable;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.example.cinemaservice.dto.ReservationDto;
import org.example.cinemaservice.dto.UpdateReservationStatusDto;
import org.example.cinemaservice.model.Reservation;
import org.springframework.stereotype.Repository;

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
    public List<ReservationDto> readAll(@Nullable Long hallId,
                                        @Nullable Long seatId,
                                        @Nullable Long movieId,
                                        @Nullable Long sessionId,
                                        @Nullable Long userId) {
        String readAll = "SELECT r FROM Reservation r WHERE 1=1";
        if (hallId != null) {
            readAll += " AND r.session.hall.id = :hallId";
        }
        if (seatId != null) {
            readAll += " AND r.id IN (SELECT rs.reservation.id FROM ReservationSeat rs WHERE rs.seat.id = :seatId)";
        }
        if (movieId != null) {
            readAll += " AND r.session.movie.id = :movieId";
        }
        if (sessionId != null) {
            readAll += " AND r.session.id = :sessionId";
        }
        if (userId != null) {
            readAll += " AND r.user.id = :userId";
        }

        TypedQuery<Reservation> query = em.createQuery(readAll, Reservation.class);

        if (hallId != null) {
            query.setParameter("hallId", hallId);
        }
        if (seatId != null) {
            query.setParameter("seatId", seatId);
        }
        if (movieId != null) {
            query.setParameter("movieId", movieId);
        }
        if (sessionId != null) {
            query.setParameter("sessionId", sessionId);
        }
        if (userId != null) {
            query.setParameter("userId", userId);
        }

        return query.getResultList().stream()
                .map(ReservationDto::ofEntity)
                .toList();
    }

    @Override
    public ReservationDto update(Reservation newReservation) {
        return ReservationDto.ofEntity(em.merge(newReservation));
    }

    @Override
    public boolean updateStatus(UpdateReservationStatusDto updateReservationStatus) {
        Query query = em.createQuery("UPDATE Reservation r SET r.status = :status WHERE r.id = :id");
        query.setParameter("status", updateReservationStatus.getStatus());
        query.setParameter("id", updateReservationStatus.getReservationId());
        return query.executeUpdate() > 0;
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
