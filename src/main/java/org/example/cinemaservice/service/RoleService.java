package org.example.cinemaservice.service;

import org.example.cinemaservice.dto.RoleDto;
import org.example.cinemaservice.model.Role;

import java.util.List;

public interface RoleService {
    RoleDto createRole(Role role);

    RoleDto getRoleById(Long id);

    RoleDto getRoleByName(String roleName);

    List<RoleDto> getAllRoles();

    RoleDto updateRole(Role upRole);

    boolean deleteRoleById(Long id);
}
