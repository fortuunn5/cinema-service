package org.example.cinemaservice.service;

import lombok.RequiredArgsConstructor;
import org.example.cinemaservice.dto.RoleDto;
import org.example.cinemaservice.model.Role;
import org.example.cinemaservice.repository.RoleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
//todo: нужен ли контроллер
@Service
@RequiredArgsConstructor
@Transactional
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    @Override
    public RoleDto createRole(Role role) {
        if (role.getId() != null) {
            throw new IllegalArgumentException("Role id already exists");
        }
        return roleRepository.save(role);
    }

    @Override
    public RoleDto getRoleById(Long id) {
        RoleDto role = roleRepository.readById(id);
        if (role == null) {
            throw new IllegalArgumentException("Role not found");
        }
        return role;
    }

    @Override
    public RoleDto getRoleByName(String roleName) {
        RoleDto role = roleRepository.readByRoleName(roleName);
        if (role == null) {
            throw new IllegalArgumentException("Role not found");
        }
        return role;
    }

    @Override
    public List<RoleDto> getAllRoles() {
        return roleRepository.readAll();
    }

    @Override
    public RoleDto updateRole(Role upRole) {
        return roleRepository.update(upRole);
    }

    @Override
    public boolean deleteRoleById(Long id) {
        if (roleRepository.deleteById(id)) {
            return true;
        }
        throw new IllegalArgumentException("Role not found");
    }
}
