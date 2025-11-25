package org.example.cinemaservice.repository;

import java.util.List;

public interface CrudRepository<T, V> {
    V save(V entity);
    V read(T id);
    List<V> readAll();
    V update(V entity);
    boolean delete(T id);
}
