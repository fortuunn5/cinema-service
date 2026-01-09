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
import java.util.Optional;

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
    public Optional<UserDto> readById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Id cannot be null");
        }

        User user = em.find(User.class, id);
        if (user != null) {
            UserDto userDto = UserDto.ofEntity(user);
            return Optional.of(userDto);
        }
        return Optional.empty();
    }

    @Override
    public Optional<UserDto> readByContactEmail(String contactEmail) {
        if (contactEmail == null) {
            throw new IllegalArgumentException("Email cannot be null");
        }

        TypedQuery<User> query = em.createQuery("SELECT u FROM User u WHERE contactEmail=:contactEmail", User.class);
        query.setParameter("contactEmail", contactEmail);
        User user = query.getSingleResult();
        if (user != null) {
            UserDto userDto = UserDto.ofEntity(user);
            return Optional.of(userDto);
        }

        return Optional.empty();
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
