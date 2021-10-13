package org.example.dao;

import org.example.model.Author;

import java.util.List;

public interface AuthorDao {
    Author getById(long id);

    List<Author> getAll();

    long insert(Author author);

    void deleteById(long id);
}
