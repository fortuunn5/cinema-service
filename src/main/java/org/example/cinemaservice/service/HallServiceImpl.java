package org.example.cinemaservice.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.cinemaservice.dto.HallDto;
import org.example.cinemaservice.model.Hall;
import org.example.cinemaservice.observer.event.hall.DeleteHallEvent;
import org.example.cinemaservice.observer.event.hall.SaveHallEvent;
import org.example.cinemaservice.observer.publisher.HallPublisher;
import org.example.cinemaservice.repository.HallRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class HallServiceImpl implements HallService {
    private final HallRepository hallRepository;
    private final HallPublisher hallPublisher;

    @Override
    public HallDto createHall(Hall newHall) {
        if (newHall.getId() != null) {
            throw new IllegalArgumentException("Hall id already exists");
        }
        hallPublisher.publishEvent(new SaveHallEvent(newHall, LocalDateTime.now()));
        return hallRepository.save(newHall);
    }

    @Override
    public HallDto getHallById(Long id) {
        HallDto hall = hallRepository.readById(id);
        if (hall == null) {
            throw new IllegalArgumentException("Hall not found");
        }
        return hall;
    }

    @Override
    public List<HallDto> getAllHalls() {
        return hallRepository.readAll();
    }

    @Override
    public HallDto updateHall(Hall upHall) {
        //todo check
//        if (upHall.getId() == null || hallRepository.readById(upHall.getId()) == null) {
//            throw new IllegalArgumentException("Hall not found");
//        }
        hallPublisher.publishEvent(new SaveHallEvent(upHall, LocalDateTime.now()));
        return hallRepository.update(upHall);
    }

    @Override
    public boolean deleteHallById(Long id) {
        hallPublisher.publishEvent(new DeleteHallEvent(id, LocalDateTime.now()));
        if (hallRepository.deleteById(id)) {
            return true;
        }
        throw new IllegalArgumentException("Hall not found");
    }
}
