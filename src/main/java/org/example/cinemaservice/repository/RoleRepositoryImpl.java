package org.example.cinemaservice.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.example.cinemaservice.dto.RoleDto;
import org.example.cinemaservice.model.Role;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class RoleRepositoryImpl implements RoleRepository {
    @PersistenceContext
    private final EntityManager em;

    @Override
    public RoleDto save(Role newRole) {
        em.persist(newRole);
        return RoleDto.ofEntity(newRole);
    }

    @Override
    public Optional<RoleDto> readById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Id cannot be null");
        }

        Role role = em.find(Role.class, id);
        if (role != null) {
            RoleDto roleDto = RoleDto.ofEntity(role);
            return Optional.of(roleDto);
        }
        return Optional.empty();
    }

    @Override
    public Optional<RoleDto> readByRoleName(String roleName) {
        if (StringUtils.isBlank(roleName)) {
            throw new IllegalArgumentException("Role name cannot be null");
        }

        TypedQuery<Role> query = em.createQuery("SELECT r FROM Role r WHERE r.role=:roleName", Role.class);
        query.setParameter("roleName", roleName);
        Role role = query.getSingleResult();
        if (role != null) {
            RoleDto roleDto = RoleDto.ofEntity(role);
            return Optional.of(roleDto);
        }
        return Optional.empty();
    }

    @Override
    public List<RoleDto> readAll() {
        TypedQuery<Role> query = em.createQuery("SELECT r FROM Role r", Role.class);
        List<Role> roles = query.getResultList();
        return roles.stream()
                .map(RoleDto::ofEntity)
                .toList();
    }

    @Override
    public RoleDto update(Role upRole) {
        return RoleDto.ofEntity(em.merge(upRole));
    }

    @Override
    public boolean deleteById(Long id) {
        Query query = em.createQuery("DELETE FROM Role r WHERE r.id = :id");
        query.setParameter("id", id);
        return query.executeUpdate() > 0;
    }
}
