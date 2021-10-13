package org.example.dao;

import org.example.model.Book;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.List;

public class BookDaoJdbc implements BookDao {
    private final NamedParameterJdbcTemplate jdbc;

    public BookDaoJdbc(NamedParameterJdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public Book getById(long id) {
        return null;
    }

    @Override
    public List<Book> getAll() {
        return null;
    }

    @Override
    public long insert(Book book) {
        return 0;
    }

    @Override
    public void deleteById(long id) {

    }
}
