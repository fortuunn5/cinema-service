package org.example.cinemaservice.service;

import org.example.cinemaservice.dto.HallDto;
import org.example.cinemaservice.model.Hall;

import java.util.List;

public interface HallService {
    HallDto createHall(Hall newHall);
    HallDto getHallById(Long id);
    List<HallDto> getAllHalls();
    HallDto updateHall(Hall upHall);
    boolean deleteHallById(Long id);
}
