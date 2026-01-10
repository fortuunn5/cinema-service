package org.example.cinemaservice.controller;

import lombok.RequiredArgsConstructor;
import org.example.cinemaservice.dto.RoleDto;
import org.example.cinemaservice.dto.UserDto;
import org.example.cinemaservice.model.User;
import org.example.cinemaservice.service.RoleService;
import org.example.cinemaservice.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RegisterController {
    private final UserService userService;
    private final RoleService roleService;

    @PostMapping("/register")
    public ResponseEntity<UserDto> registerUser(@RequestBody UserDto userDto) {
        //todo: enum
        RoleDto role = roleService.getRoleByName("ROLE_USER");
        User user = new User(userDto.getContactEmail(), userDto.getPassword(), role.toEntity());

        return new ResponseEntity<>(userService.createUser(UserDto.ofEntity(user)), HttpStatus.CREATED);
    }
}
