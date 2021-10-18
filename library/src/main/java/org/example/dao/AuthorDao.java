package org.example.dao;

import org.example.model.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorDao {
    Author getById(long id);

    Optional<Author> getByIdOptional(long id);

    List<Author> getAll();

    long insert(Author author);

    void update(Author author);

    void deleteById(long id);
}
