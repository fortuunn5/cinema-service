package org.example.cinemaservice.repository;

import org.example.cinemaservice.dto.HallDto;
import org.example.cinemaservice.model.Hall;

import java.util.List;

public interface HallRepository {
    HallDto save(Hall newHall);
    HallDto readById(Long id);
    List<HallDto> readAll();
    HallDto update(Hall upHall);
    boolean deleteById(Long id);
}
