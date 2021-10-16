package org.example.dao;

import org.example.model.Genre;

import java.util.List;

public interface GenreDao {
    Genre getById(long id);

    List<Genre> getAll();

    long insert(Genre genre);

    void update(Genre genre);

    void deleteById(long id);
}
