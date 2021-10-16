package org.example.dao;

import org.example.model.Author;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class AuthorDaoJdbc implements AuthorDao {
    private static final RowMapper<Author> mapper = ((rs, rowNum) ->
            new Author(rs.getLong("id"), rs.getString("name")));

    private final NamedParameterJdbcTemplate jdbc;

    public AuthorDaoJdbc(NamedParameterJdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public Author getById(long id) {
        return jdbc.queryForObject("select id, name from authors where id = :id", Map.of("id", id), mapper);
    }

    @Override
    public List<Author> getAll() {
        return jdbc.query("select * from authors", mapper);
    }

    @Override
    public long insert(Author author) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update("insert into authors (name) values (:name)",
                new MapSqlParameterSource("name", author.getName()), keyHolder);
        long id = keyHolder.getKey().longValue();
        author.setId(id);
        return id;
    }

    @Override
    public void update(Author author) {
        jdbc.update("update authors set name = :name where id = :id",
                Map.of("id", author.getId(), "name", author.getName()));
    }

    @Override
    public void deleteById(long id) {
        jdbc.update("delete from authors where id = :id", Map.of("id", id));
    }
}
