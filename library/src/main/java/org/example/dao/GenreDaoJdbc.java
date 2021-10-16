package org.example.dao;

import org.example.model.Genre;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class GenreDaoJdbc implements GenreDao {
    private static final RowMapper<Genre> mapper = ((rs, rowNum) ->
            new Genre(rs.getLong("id"), rs.getString("name")));

    private final NamedParameterJdbcTemplate jdbc;

    public GenreDaoJdbc(NamedParameterJdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public Genre getById(long id) {
        return jdbc.queryForObject("select * from genres where id = :id", Map.of("id", id), mapper);
    }

    @Override
    public List<Genre> getAll() {
        return jdbc.query("select * from genres", mapper);
    }

    @Override
    public long insert(Genre genre) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update("insert into genres (name) values (:name)",
                new MapSqlParameterSource("name", genre.getName()), keyHolder);
        long id = keyHolder.getKey().longValue();
        genre.setId(id);
        return id;
    }

    @Override
    public void update(Genre genre) {
        jdbc.update("update genres set name = :name where id = :id",
                Map.of("id", genre.getId(), "name", genre.getName()));
    }

    @Override
    public void deleteById(long id) {
        jdbc.update("delete from genres where id = :id", Map.of("id", id));
    }
}
