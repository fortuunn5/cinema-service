package org.example.cinemaservice.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.cinemaservice.dto.UserDto;
import org.example.cinemaservice.repository.UserRepository;
import org.example.cinemaservice.utils.SecurityUtils;
import org.springframework.stereotype.Service;

import java.util.List;
//todo: переделать проверки контекста
@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleService roleService;

    @Override
    public UserDto createUser(UserDto newUser) {
        if (newUser.getId() != null) {
            throw new IllegalArgumentException("User id already exists");
        }
        if (userRepository.readByContactEmail(newUser.getContactEmail()) != null) {
            throw new IllegalArgumentException("Contact email already exists");
        }
        //todo:доделать
//        newUser.setRole(Role.USER);
        return userRepository.save(newUser.toEntity());
    }

    @Override
    public UserDto getUserById(Long id) {
        if (SecurityUtils.getCurrentRole(this, this.roleService).getRole().equals("ROLE_USER") && !SecurityUtils.getCurrentUserId(this).equals(id)) {
            throw new IllegalArgumentException("Not enough rights");
        }
        UserDto userById = userRepository.readById(id);
        if (userById == null) {
            throw new IllegalArgumentException("User not found");
        }
        return userById;
    }

    @Override
    public UserDto getUserByContactEmail(String contactEmail) {
//        if (SecurityUtils.getCurrentRole(this, this.roleService).getRole().equals("ROLE_USER") && !SecurityUtils.getCurrentUserEmail().equals(contactEmail)) {
//            throw new IllegalArgumentException("Not enough rights");
//        }
        if (SecurityUtils.hasRole("ROLE_ADMIN")) {
            return new UserDto();
        }
        UserDto user = userRepository.readByContactEmail(contactEmail);
        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }//todo доделать
        return user;
    }

    @Override
    public UserDto getUserByEmail(String email) {
        return userRepository.readByContactEmail(email);
    }

    @Override
    public List<UserDto> getAllUsers() {
        return userRepository.readAll();
    }

    @Override
    public UserDto updateUser(UserDto upUser) {
        if (SecurityUtils.getCurrentRole(this, this.roleService).getRole().equals("ROLE_USER") && !SecurityUtils.getCurrentUserId(this).equals(upUser.getId())) {
            throw new IllegalArgumentException("Not enough rights");
        }
        UserDto userByContactEmail = userRepository.readByContactEmail(upUser.getContactEmail());
        if (userByContactEmail != null && !userByContactEmail.getId().equals(upUser.getId())) {
            throw new IllegalArgumentException("Contact email already exists");
        }
        return userRepository.update(upUser.toEntity());
    }

    @Override
    public boolean deleteUserById(Long id) {
        if (userRepository.deleteById(id)) {
            return true;
        }
        throw new IllegalArgumentException("User not found");
    }
}
