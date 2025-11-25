package org.example.cinemaservice.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.cinemaservice.dto.HallDto;
import org.example.cinemaservice.dto.SeatDto;
import org.example.cinemaservice.model.Hall;
import org.example.cinemaservice.model.Seat;
import org.example.cinemaservice.repository.SeatRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class SeatServiceImpl implements SeatService {
    private final SeatRepository seatRepository;
    private final HallService hallService;

    @Override
    public SeatDto createSeat(Seat newSeat) {
        if (newSeat.getId() != null) {
            throw new IllegalArgumentException("Seat id already exists");
        }
        HallDto hall = hallService.getHallById(newSeat.getHall().getId());
        //todo: проверить
        if (hall.getSeatsId().size() == hall.getCapacity()) {
            throw new IllegalArgumentException("Seat capacity exceeded");
        }
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
    public SeatDto updateSeat(Seat upSeat) {
        if (upSeat.getId() == null || seatRepository.readById(upSeat.getId()) == null) {
            throw new IllegalArgumentException("Seat not found");
        }
        return seatRepository.update(upSeat);
    }

    @Override
    public boolean deleteSeatById(Long id) {
        if (seatRepository.deleteById(id)) {
            return true;
        }
        throw new IllegalArgumentException("Seat not found");
    }
}
