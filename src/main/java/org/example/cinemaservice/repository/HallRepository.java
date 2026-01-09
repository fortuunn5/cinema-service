package org.example.cinemaservice.repository;

import org.example.cinemaservice.dto.HallDto;
import org.example.cinemaservice.model.Hall;

import java.util.List;
import java.util.Optional;

public interface HallRepository {
    HallDto save(Hall newHall);

    Optional<HallDto> readById(Long id);

    List<HallDto> readAll();

    HallDto update(Hall upHall);

    boolean deleteById(Long id);
}
