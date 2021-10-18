package org.example.dao;

import org.example.exception.LibraryDataAccessException;
import org.example.model.Author;
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
public class AuthorDaoJdbc implements AuthorDao {
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthorDaoJdbc.class);
    private static final RowMapper<Author> mapper = ((rs, rowNum) ->
            new Author(rs.getLong("id"), rs.getString("name")));

    private final NamedParameterJdbcTemplate jdbc;

    public AuthorDaoJdbc(NamedParameterJdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public Author getById(long id) {
        Author author;
        try {
            author = jdbc.queryForObject("select id, name from authors where id = :id", Map.of("id", id), mapper);
        } catch (DataAccessException e) {
            LOGGER.error("Can't find author by id `{}`, cause: `{}`", id, e.getMessage());
            throw new LibraryDataAccessException(e.getMessage(), e);
        }
        return author;
    }

    @Override
    public Optional<Author> getByIdOptional(long id) {
        Author author;
        try {
            author = getById(id);
        } catch (LibraryDataAccessException e) {
            return Optional.empty();
        }
        return Optional.of(author);
    }

    @Override
    public List<Author> getAll() {
        List<Author> authors;
        try {
            authors = jdbc.query("select * from authors", mapper);
        } catch (DataAccessException e) {
            LOGGER.error("Can't get all authors, cause: `{}`", e.getMessage());
            throw new LibraryDataAccessException(e.getMessage(), e);
        }
        return authors;
    }

    @Override
    public long insert(Author author) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        try {
            jdbc.update("insert into authors (name) values (:name)",
                    new MapSqlParameterSource("name", author.getName()), keyHolder);
        } catch (DataAccessException e) {
            LOGGER.error("Can't insert author `{}`, cause: `{}`", author, e.getMessage());
            throw new LibraryDataAccessException(e.getMessage(), e);
        }

        long id = Optional.ofNullable(keyHolder.getKey())
                .map(Number::longValue)
                .orElseThrow(() -> new LibraryDataAccessException("Failed to get author id, the keyHolder.getKey() is null"));

        author.setId(id);

        return id;
    }

    @Override
    public void update(Author author) {
        try {
            jdbc.update("update authors set name = :name where id = :id",
                    Map.of("id", author.getId(), "name", author.getName()));
        } catch (DataAccessException e) {
            LOGGER.error("Can't update author `{}`, cause: `{}`", author, e.getMessage());
            throw new LibraryDataAccessException(e.getMessage(), e);
        }
    }

    @Override
    public void deleteById(long id) {
        try {
            jdbc.update("delete from authors where id = :id", Map.of("id", id));
        } catch (DataAccessException e) {
            LOGGER.error("Can't delete author by id `{}`, cause: `{}`", id, e.getMessage());
            throw new LibraryDataAccessException(e.getMessage(), e);
        }
    }
}
