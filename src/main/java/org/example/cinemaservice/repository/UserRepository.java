package org.example.cinemaservice.repository;

import org.example.cinemaservice.dto.UserDto;
import org.example.cinemaservice.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    UserDto save(User newUser);

    Optional<UserDto> readById(Long id);

    Optional<UserDto> readByContactEmail(String contactEmail);

    List<UserDto> readAll();

    UserDto update(User upUser);

    boolean deleteById(Long id);
}
