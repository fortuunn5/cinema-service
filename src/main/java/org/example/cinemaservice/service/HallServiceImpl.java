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
import java.util.Optional;

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
        Optional<HallDto> hallDto = hallRepository.readById(id);
        return hallDto.orElseThrow(() -> new IllegalArgumentException("Hall with id " + id + " not found"));
    }

    @Override
    public List<HallDto> getAllHalls() {
        return hallRepository.readAll();
    }

    @Override
    public HallDto updateHall(Hall upHall) {
        Hall hall = getHallById(upHall.getId()).toEntity();
        if (upHall.getName() != null) {
            hall.setName(upHall.getName());
        }
        if (upHall.getCapacity() != null) {
            hall.setCapacity(upHall.getCapacity());
        }

        hallPublisher.publishEvent(new SaveHallEvent(hall, LocalDateTime.now()));
        return hallRepository.update(hall);
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
