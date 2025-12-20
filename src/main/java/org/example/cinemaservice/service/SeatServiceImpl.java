package org.example.cinemaservice.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.cinemaservice.dto.HallDto;
import org.example.cinemaservice.dto.SeatDto;
import org.example.cinemaservice.dto.SeatWithIsReservedDto;
import org.example.cinemaservice.model.Seat;
import org.example.cinemaservice.observer.event.seat.DeleteSeatEvent;
import org.example.cinemaservice.observer.event.seat.SaveSeatEvent;
import org.example.cinemaservice.observer.publisher.SeatPublisher;
import org.example.cinemaservice.repository.SeatRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class SeatServiceImpl implements SeatService {
    private final SeatRepository seatRepository;
    private final HallService hallService;
    private final SeatPublisher seatPublisher;

    @Override
    public SeatDto createSeat(Seat newSeat) {
        if (newSeat.getId() != null) {
            throw new IllegalArgumentException("Seat id already exists");
        }
        HallDto hall = hallService.getHallById(newSeat.getHall().getId());
        //todo: проверить
        seatPublisher.publishEvent(new SaveSeatEvent(newSeat.getId(), LocalDateTime.now(), hall.getId()));
        return seatRepository.save(newSeat);
    }

    @Override
    public SeatDto getSeatById(Long id) {
        SeatDto seat = seatRepository.readById(id);
        if (seat == null) {
            throw new IllegalArgumentException("Seat not found");
        }
        return seat;
    }

    @Override
    public List<SeatDto> getAllSeats() {
        return seatRepository.readAll();
    }

    @Override
    public List<SeatDto> getAllSeatsByHallId(Long hallId) {
        return seatRepository.readAllByHallId(hallId);
    }

    @Override
    public SeatDto updateSeat(Seat upSeat) {
        //todo check
//        if (upSeat.getId() == null || seatRepository.readById(upSeat.getId()) == null) {
//            throw new IllegalArgumentException("Seat not found");
//        }
        seatPublisher.publishEvent(new SaveSeatEvent(upSeat.getId(), LocalDateTime.now(), upSeat.getHall().getId()));
        return seatRepository.update(upSeat);
    }

    @Override
    public boolean deleteSeatById(Long id) {
        seatPublisher.publishEvent(new DeleteSeatEvent(id, LocalDateTime.now()));
        if (seatRepository.deleteById(id)) {
            return true;
        }
        throw new IllegalArgumentException("Seat not found");
    }

    @Override
    public int deleteAllSeatsByHallId(Long hallId) {
        List<SeatDto> seatsByHall = getAllSeatsByHallId(hallId);
        seatsByHall.forEach(x -> deleteSeatById(x.getId()));
        return seatsByHall.size();
    }

    @Override
    public List<SeatWithIsReservedDto> getSeatsWithIsFreeBySessionId(Long sessionId) {
        if (sessionId == null) {
            throw new IllegalArgumentException("Session id can not null");
        }
        List<SeatWithIsReservedDto> seatsWithIsFreeBySessionId = seatRepository.readWithIsFreeBySessionId(sessionId);
        if (seatsWithIsFreeBySessionId.isEmpty()) {
            throw new IllegalArgumentException("Seats or session not found");
        }
        return seatsWithIsFreeBySessionId;
    }
}
