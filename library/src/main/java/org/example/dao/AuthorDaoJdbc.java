package org.example.dao;

import org.example.model.Author;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AuthorDaoJdbc implements AuthorDao {
    private final NamedParameterJdbcTemplate jdbc;

    public AuthorDaoJdbc(NamedParameterJdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public Author getById(long id) {
        return null;
    }

    @Override
    public List<Author> getAll() {
        return null;
    }

    @Override
    public long insert(Author author) {
        return 0;
    }

    @Override
    public void deleteById(long id) {

    }
}
