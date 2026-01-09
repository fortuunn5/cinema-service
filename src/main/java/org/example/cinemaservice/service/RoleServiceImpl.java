package org.example.cinemaservice.service;

import lombok.RequiredArgsConstructor;
import org.example.cinemaservice.dto.RoleDto;
import org.example.cinemaservice.model.Role;
import org.example.cinemaservice.repository.RoleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

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
        Optional<RoleDto> roleDto = roleRepository.readById(id);
        return roleDto.orElseThrow(() -> new IllegalArgumentException("Role with id " + id + " not found"));
    }

    @Override
    public RoleDto getRoleByName(String roleName) {
        Optional<RoleDto> role = roleRepository.readByRoleName(roleName);
        return role.orElseThrow(() -> new IllegalArgumentException("Role with name " + roleName + " not found"));
    }

    @Override
    public List<RoleDto> getAllRoles() {
        return roleRepository.readAll();
    }

    @Override
    public RoleDto updateRole(Role upRole) {
        Role roleDto = getRoleById(upRole.getId()).toEntity();

        if (upRole.getRole() != null) {
            roleDto.setRole(upRole.getRole());
        }
        if (upRole.getDescription() != null) {
            roleDto.setDescription(upRole.getDescription());
        }
        return roleRepository.update(roleDto);
    }

    @Override
    public boolean deleteRoleById(Long id) {
        if (roleRepository.deleteById(id)) {
            return true;
        }
        throw new IllegalArgumentException("Role not found");
    }
}
