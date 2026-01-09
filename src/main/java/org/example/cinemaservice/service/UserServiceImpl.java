package org.example.cinemaservice.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.example.cinemaservice.dto.UserDto;
import org.example.cinemaservice.repository.UserRepository;
import org.example.cinemaservice.utils.SecurityUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
        try {
            newUser.setRoleId(roleService.getRoleByName("ROLE_USER").getId());
            return userRepository.save(newUser.toEntity());
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException(e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public UserDto getUserById(Long id) {
        Optional<UserDto> userById = userRepository.readById(id);
        if (userById.isEmpty()) {
            throw new IllegalArgumentException("User with id " + id + " not found");
        }
        if (SecurityUtils.hasRole("ROLE_ADMIN") || StringUtils.equalsIgnoreCase(userById.get().getContactEmail(), SecurityUtils.getCurrentUserEmail())) {
            return userById.get();
        }
        throw new RuntimeException("Not enough rights");
    }

    @Override
    public UserDto getUserByContactEmail(String contactEmail) {
        Optional<UserDto> userByEmail = userRepository.readByContactEmail(contactEmail);
        if (userByEmail.isEmpty()) {
            throw new IllegalArgumentException("User with email " + contactEmail + " not found");
        }
        if (SecurityUtils.hasRole("ROLE_ADMIN") || StringUtils.equalsIgnoreCase(userByEmail.get().getContactEmail(), SecurityUtils.getCurrentUserEmail())) {
            return userByEmail.get();
        }
        throw new RuntimeException("Not enough rights");
    }

    @Override
    public UserDto getUserByEmail(String email) {
        Optional<UserDto> userDto = userRepository.readByContactEmail(email);
        return userDto.orElseThrow(() -> new IllegalArgumentException("User with email " + email + " not found"));
    }

    @Override
    public List<UserDto> getAllUsers() {
        return userRepository.readAll();
    }

    @Override
    public UserDto updateUser(UserDto upUser) {
        UserDto userDto = getUserById(upUser.getId());
        if (upUser.getContactEmail() != null) {
            userDto.setContactEmail(upUser.getContactEmail());
        }
        if (upUser.getPassword() != null) {
            userDto.setPassword(upUser.getPassword());
        }
        if (upUser.getRoleId() != null && SecurityUtils.hasRole("ROLE_ADMIN")) {
            userDto.setRoleId(upUser.getRoleId());
        }

        return userRepository.update(userDto.toEntity());
    }

    @Override
    public boolean deleteUserById(Long id) {
        if (userRepository.deleteById(id)) {
            return true;
        }
        throw new IllegalArgumentException("User not found");
    }
}
