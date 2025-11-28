package org.example.cinemaservice.repository;


import jakarta.annotation.Nullable;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.example.cinemaservice.dto.MovieDto;
import org.example.cinemaservice.model.Genre;
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
    public List<MovieDto> readAll(@Nullable List<Genre> genres) {
        String readAll = "SELECT m FROM Movie m WHERE 1=1";

        if (genres != null && genres.isEmpty()) {
            readAll += "AND WHERE (SELECT COUNT(DISTINCT g) FROM Movie m2 JOIN m2.genres g WHERE m2=m AND g IN :genres) = :countGenres";
        }

        TypedQuery<Movie> query = em.createQuery(readAll, Movie.class);

        if (genres != null && genres.isEmpty()) {
            query.setParameter("genres", genres);
            query.setParameter("countGenres", genres.size());
        }
        List<MovieDto> movieDtoList = new ArrayList<>();
        List<Movie> movieList = query.getResultList();
        for (Movie movie : movieList) {
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
        Query query = em.createQuery("DELETE FROM Movie m WHERE m.id = :id");
        query.setParameter("id", id);
        int count = query.executeUpdate();
        return count != 0;
    }
}
