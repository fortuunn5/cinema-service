package org.example.cinemaservice.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.example.cinemaservice.dto.UserDto;
import org.example.cinemaservice.model.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {
    @PersistenceContext
    private final EntityManager em;

    @Override
    public UserDto save(User newUser) {
        em.persist(newUser);
        return UserDto.ofEntity(newUser);
    }

    @Override
    public UserDto readById(Long id) {
        return UserDto.ofEntity(em.find(User.class, id));
    }

    @Override
    public UserDto readByContactEmail(String contactEmail) {
        TypedQuery<User> query = em.createQuery("SELECT u FROM User u WHERE contactEmail=:contactEmail", User.class);
        query.setParameter("contactEmail", contactEmail);
        User singleResult = query.getSingleResult();
        return UserDto.ofEntity(singleResult);
    }

    @Override
    public List<UserDto> readAll() {
        TypedQuery<User> query = em.createQuery("SELECT u FROM User u", User.class);
        List<User> userList = query.getResultList();
        return userList.stream().map(UserDto::ofEntity).toList();
    }

    @Override
    public UserDto update(User upUser) {
        return UserDto.ofEntity(em.merge(upUser));
    }

    @Override
    public boolean deleteById(Long id) {
        Query query = em.createQuery("DELETE FROM User u WHERE u.id = :id");
        query.setParameter("id", id);
        int count = query.executeUpdate();
        return count != 0;
    }
}
