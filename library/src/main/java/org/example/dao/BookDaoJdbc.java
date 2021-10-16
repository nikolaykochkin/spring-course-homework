package org.example.dao;

import org.example.model.Author;
import org.example.model.Book;
import org.example.model.Genre;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Repository
public class BookDaoJdbc implements BookDao {
    private final NamedParameterJdbcTemplate jdbc;

    public BookDaoJdbc(NamedParameterJdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public Book getById(long id) {
        return jdbc.queryForObject(
                "select b.id, b.name, b.author_id, a.name author_name, b.genre_id, g.name genre_name " +
                        "from books b " +
                        "left join authors a on a.id = b.author_id " +
                        "left join genres g on g.id = b.genre_id " +
                        "where b.id = :id",
                Map.of("id", id), new BookMapper());
    }

    @Override
    public List<Book> getAll() {
        return jdbc.query(
                "select b.id, b.name, b.author_id, a.name author_name, b.genre_id, g.name genre_name " +
                        "from books b " +
                        "left join authors a on a.id = b.author_id " +
                        "left join genres g on g.id = b.genre_id",
                new BookMapper()
        );
    }

    @Override
    public long insert(Book book) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue("name", book.getName());
        source.addValue("author_id", book.getAuthor().getId());
        source.addValue("genre_id", book.getGenre().getId());
        jdbc.update("insert into books ( name, author_id, genre_id ) values ( :name, :author_id, :genre_id )", source, keyHolder);
        long id = keyHolder.getKey().longValue();
        book.setId(id);
        return id;
    }

    @Override
    public void update(Book book) {
        jdbc.update("update books set name = :name, author_id = :author_id, genre_id = :genre_id where id = :id",
                Map.of(
                        "id", book.getId(),
                        "name", book.getName(),
                        "author_id", book.getAuthor().getId(),
                        "genre_id", book.getGenre().getId()
                ));
    }

    @Override
    public void deleteById(long id) {
        jdbc.update("delete from books where id = :id", Map.of("id", id));
    }

    public static class BookMapper implements RowMapper<Book> {
        @Override
        public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
            Author author = new Author(rs.getLong("author_id"), rs.getString("author_name"));
            Genre genre = new Genre(rs.getLong("genre_id"), rs.getString("genre_name"));
            return new Book(
                    rs.getLong("id"),
                    rs.getString("name"),
                    author,
                    genre
            );
        }
    }
}
