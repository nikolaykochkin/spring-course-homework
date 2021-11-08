package org.example.dao;

import org.example.model.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreDao {
    Optional<Genre> findById(long id);

    List<Genre> findAll();

    Genre save(Genre genre);

    void update(Genre genre);

    void deleteById(long id);
}
