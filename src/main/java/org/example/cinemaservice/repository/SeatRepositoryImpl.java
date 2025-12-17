package org.example.cinemaservice.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.example.cinemaservice.dto.SeatDto;
import org.example.cinemaservice.dto.SeatWithIsReservedDto;
import org.example.cinemaservice.model.Seat;
import org.example.cinemaservice.model.Status;
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
    public List<SeatDto> readAllByHallId(Long hallId) {
        TypedQuery<Seat> query = em.createQuery("SELECT s FROM Seat s WHERE s.hall.id = :hallId", Seat.class);
        query.setParameter("hallId", hallId);
        return query.getResultList().stream().map(SeatDto::ofEntity).toList();
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

    @Override
    public int deleteAllByHallId(Long hallId) {
        Query deleteSeatsQuery = em.createQuery("DELETE FROM Seat s WHERE s.hall.id = :hallId");
        deleteSeatsQuery.setParameter("hallId", hallId);
        return deleteSeatsQuery.executeUpdate();
    }

    @Override
    public List<SeatWithIsReservedDto> readWithIsFreeBySessionId(Long sessionId) {
        String qString = """
                SELECT new org.example.cinemaservice.dto.SeatWithIsReservedDto(
                    s.id,
                    st.hall.id,
                    st.id,
                    st.row,
                    st.number,
                    (CASE
                        WHEN EXISTS(
                            SELECT 1
                            FROM ReservationSeat rs
                            JOIN rs.reservation r
                            WHERE rs.seat.id = st.id
                            AND r.session.id = :sessionId
                            AND r.status IN (:statuses)
                        ) THEN false
                        ELSE true
                    END)
                )
                FROM Seat st
                JOIN Session s ON st.hall.id = s.hall.id
                WHERE s.id = :sessionId
                """;

        TypedQuery<SeatWithIsReservedDto> query = em.createQuery(qString, SeatWithIsReservedDto.class);
        query.setParameter("sessionId", sessionId);
        query.setParameter("statuses", List.of(Status.RESERVED, Status.PAID));
        return query.getResultList();
    }
}
