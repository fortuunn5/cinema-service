package org.example.cinemaservice.repository;


import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.example.cinemaservice.dto.MovieDto;
import org.example.cinemaservice.model.Movie;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MovieRepositoryImpl implements MovieRepository {
    @PersistenceContext
    private final EntityManager em;

    @Override
    public MovieDto save(Movie newMovie) {
        em.persist(newMovie);
        return MovieDto.ofEntity(newMovie);
    }

    @Override
    public MovieDto readById(Long id) {
        return MovieDto.ofEntity(em.find(Movie.class, id));
    }

    @Override
    public MovieDto readByName(String name) {
        TypedQuery<Movie> query = em.createQuery("SELECT m FROM Movie m WHERE m.name = :name", Movie.class);
        query.setParameter("name", name);
        return MovieDto.ofEntity(query.getSingleResult());
    }

    @Override
    public List<MovieDto> readAll() {
        TypedQuery<Movie> query = em.createQuery("SELECT m FROM Movie m", Movie.class);
        List<MovieDto> movieDtoList = new ArrayList<>();
        for (Movie movie : query.getResultList()) {
            movieDtoList.add(MovieDto.ofEntity(movie));
        }
        return movieDtoList;
    }

    @Override
    public MovieDto update(Movie entity) {
        return MovieDto.ofEntity(em.merge(entity));
    }

    @Override
    public boolean deleteById(Long id) {
//        em.remove(em.find(Movie.class, id));
        Query query = em.createQuery("DELETE FROM Movie m WHERE m.id = :id");
        query.setParameter("id", id);
        int count = query.executeUpdate();
        return count != 0;
    }
}
