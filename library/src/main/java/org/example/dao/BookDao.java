package org.example.dao;

import org.example.model.Book;

import java.util.List;

public interface BookDao {
    Book getById(long id);

    List<Book> getAll();

    long insert(Book book);

    void deleteById(long id);
}
