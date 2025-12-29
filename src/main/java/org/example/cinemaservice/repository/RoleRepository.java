package org.example.cinemaservice.repository;

import org.example.cinemaservice.dto.RoleDto;
import org.example.cinemaservice.model.Role;

import java.util.List;

public interface RoleRepository {
    RoleDto save(Role newRole);

    RoleDto readById(Long id);

    RoleDto readByRoleName(String roleName);

    List<RoleDto> readAll();

    RoleDto update(Role upRole);

    boolean deleteById(Long id);
}
