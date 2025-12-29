package org.example.cinemaservice.service;

import org.example.cinemaservice.dto.UserDto;

import java.util.List;

public interface UserService {
    UserDto createUser(UserDto newUser);

    UserDto getUserById(Long id);

    UserDto getUserByContactEmail(String contactEmail);

    UserDto getUserByEmail(String email);

    List<UserDto> getAllUsers();

    UserDto updateUser(UserDto upUser);

    boolean deleteUserById(Long id);
}
