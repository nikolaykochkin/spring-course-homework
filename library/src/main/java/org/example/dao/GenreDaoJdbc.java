package org.example.dao;

import org.example.model.Genre;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.List;

public class GenreDaoJdbc implements GenreDao {
    private final NamedParameterJdbcTemplate jdbc;

    public GenreDaoJdbc(NamedParameterJdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public Genre getById(long id) {
        return null;
    }

    @Override
    public List<Genre> getAll() {
        return null;
    }

    @Override
    public long insert(Genre genre) {
        return 0;
    }

    @Override
    public void deleteById(long id) {

    }
}
