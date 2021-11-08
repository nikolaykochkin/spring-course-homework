package org.example.dao;

import org.example.model.Book;

import java.util.List;
import java.util.Optional;

public interface BookDao {
    Optional<Book> findById(long id);

    List<Book> findAll();

    Book save(Book book);

    void update(Book book);

    void deleteById(long id);
}
