package org.example.dao;

import org.example.model.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorDao {
    Optional<Author> findById(long id);

    List<Author> findAll();

    Author save(Author author);

    void update(Author author);

    void deleteById(long id);
}
