package org.example.dao;

import org.example.model.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreDao {
    Genre getById(long id);

    Optional<Genre> getByIdOptional(long id);

    List<Genre> getAll();

    long insert(Genre genre);

    void update(Genre genre);

    void deleteById(long id);
}
