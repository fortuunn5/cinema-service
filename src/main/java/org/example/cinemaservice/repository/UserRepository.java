package org.example.cinemaservice.repository;

import org.example.cinemaservice.dto.UserDto;
import org.example.cinemaservice.model.User;

import java.util.List;

public interface UserRepository {
    UserDto save(User newUser);

    UserDto readById(Long id);

    UserDto readByContactEmail(String contactEmail);

    List<UserDto> readAll();

    UserDto update(User upUser);

    boolean deleteById(Long id);
}
