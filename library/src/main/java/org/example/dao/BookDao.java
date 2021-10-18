package org.example.dao;

import org.example.model.Book;

import java.util.List;
import java.util.Optional;

public interface BookDao {
    Book getById(long id);

    Optional<Book> getByIdOptional(long id);

    List<Book> getAll();

    long insert(Book book);

    void update(Book book);

    void deleteById(long id);
}
