package org.example.dao;

import org.example.exception.LibraryDataAccessException;
import org.example.model.Genre;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class GenreDaoJdbc implements GenreDao {
    private static final Logger LOGGER = LoggerFactory.getLogger(GenreDaoJdbc.class);
    private static final RowMapper<Genre> mapper = ((rs, rowNum) ->
            new Genre(rs.getLong("id"), rs.getString("name")));

    private final NamedParameterJdbcTemplate jdbc;

    public GenreDaoJdbc(NamedParameterJdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public Genre getById(long id) {
        try {
            return jdbc.queryForObject("select * from genres where id = :id", Map.of("id", id), mapper);
        } catch (DataAccessException e) {
            LOGGER.error("Can't find genre by id `{}`, cause: `{}`", id, e.getMessage());
            throw new LibraryDataAccessException(e.getMessage(), e);
        }
    }

    @Override
    public Optional<Genre> getByIdOptional(long id) {
        Genre genre;
        try {
            genre = getById(id);
        } catch (LibraryDataAccessException e) {
            return Optional.empty();
        }
        return Optional.of(genre);
    }

    @Override
    public List<Genre> getAll() {
        try {
            return jdbc.query("select * from genres", mapper);
        } catch (DataAccessException e) {
            LOGGER.error("Can't get all genres, cause: `{}`", e.getMessage());
            throw new LibraryDataAccessException(e.getMessage(), e);
        }
    }

    @Override
    public long insert(Genre genre) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        try {
            jdbc.update("insert into genres (name) values (:name)",
                    new MapSqlParameterSource("name", genre.getName()), keyHolder);
        } catch (DataAccessException e) {
            LOGGER.error("Can't insert genre `{}`, cause: `{}`", genre, e.getMessage());
            throw new LibraryDataAccessException(e.getMessage(), e);
        }
        long id = Optional.ofNullable(keyHolder.getKey())
                .map(Number::longValue)
                .orElseThrow(() -> new LibraryDataAccessException("Failed to get genre id, the keyHolder.getKey() is null"));
        genre.setId(id);
        return id;
    }

    @Override
    public void update(Genre genre) {
        try {
            jdbc.update("update genres set name = :name where id = :id",
                    Map.of("id", genre.getId(), "name", genre.getName()));
        } catch (DataAccessException e) {
            LOGGER.error("Can't update genre `{}`, cause: `{}`", genre, e.getMessage());
            throw new LibraryDataAccessException(e.getMessage(), e);
        }
    }

    @Override
    public void deleteById(long id) {
        try {
            jdbc.update("delete from genres where id = :id", Map.of("id", id));
        } catch (DataAccessException e) {
            LOGGER.error("Can't delete genre by id `{}`, cause: `{}`", id, e.getMessage());
            throw new LibraryDataAccessException(e.getMessage(), e);
        }
    }
}
