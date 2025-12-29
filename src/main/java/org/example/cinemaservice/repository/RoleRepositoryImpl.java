package org.example.cinemaservice.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.example.cinemaservice.dto.RoleDto;
import org.example.cinemaservice.model.Role;
import org.springframework.stereotype.Repository;

import java.util.List;

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
    public RoleDto readById(Long id) {
        return RoleDto.ofEntity(em.find(Role.class, id));
    }

    @Override
    public RoleDto readByRoleName(String roleName) {
        TypedQuery<Role> query = em.createQuery("SELECT r FROM Role r WHERE r.role=:roleName", Role.class);
        return RoleDto.ofEntity(query.getSingleResult());
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
