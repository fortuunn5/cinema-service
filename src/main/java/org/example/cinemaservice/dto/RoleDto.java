package org.example.cinemaservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.cinemaservice.model.Role;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RoleDto {
    private Long id;
    private String role;
    private String description;

    public Role toEntity() {
        Role roleEntity = new Role();
        roleEntity.setId(id);
        roleEntity.setRole(role);
        roleEntity.setDescription(description);
        return roleEntity;
    }

    public static RoleDto ofEntity(Role roleEntity) {
        RoleDto roleDto = new RoleDto();
        roleDto.setId(roleEntity.getId());
        roleDto.setRole(roleEntity.getRole());
        roleDto.setDescription(roleEntity.getDescription());
        return roleDto;
    }
}
