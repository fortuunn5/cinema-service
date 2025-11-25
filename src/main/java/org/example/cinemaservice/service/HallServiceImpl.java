package org.example.cinemaservice.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.cinemaservice.dto.HallDto;
import org.example.cinemaservice.model.Hall;
import org.example.cinemaservice.repository.HallRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class HallServiceImpl implements HallService {
    private final HallRepository hallRepository;

    @Override
    public HallDto createHall(Hall newHall) {
        if (newHall.getId() != null) {
            throw new IllegalArgumentException("Hall id already exists");
        }
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
        if (upHall.getId() == null || hallRepository.readById(upHall.getId()) == null) {
            throw new IllegalArgumentException("Hall not found");
        }
        return hallRepository.update(upHall);
    }

    @Override
    public boolean deleteHallById(Long id) {
        if (hallRepository.deleteById(id)) {
            return true;
        }
        throw new IllegalArgumentException("Hall not found");
    }
}
