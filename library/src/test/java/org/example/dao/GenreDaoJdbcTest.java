package org.example.dao;

import org.example.exception.LibraryDataAccessException;
import org.example.model.Genre;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@JdbcTest
@Import(GenreDaoJdbc.class)
class GenreDaoJdbcTest {
    @Autowired
    private GenreDaoJdbc genreDaoJdbc;

    @Test
    void should_ReturnCorrectGenre_When_GetById() {
        Genre expected = new Genre(1, "Novel");
        Genre actual = genreDaoJdbc.getById(1);
        assertEquals(expected, actual);
    }

    @Test
    void should_ReturnCorrectOptionalGenre_When_GetByIdOptional() {
        Genre expected = new Genre(1, "Novel");
        Optional<Genre> actual = genreDaoJdbc.getByIdOptional(1);
        assertEquals(expected, actual.orElse(null));
    }

    @Test
    void should_ReturnAllGenres_When_GetAll() {
        List<Genre> expected = new ArrayList<>();
        expected.add(new Genre(1, "Novel"));
        expected.add(new Genre(2, "Poem"));
        expected.add(new Genre(3, "Test"));

        List<Genre> actual = genreDaoJdbc.getAll();

        assertIterableEquals(expected, actual);
    }

    @Test
    void should_ReturnNewGenre_When_InsertNewGenre() {
        Genre expected = new Genre("Genre Genre");

        long id = genreDaoJdbc.insert(expected);
        Genre actual = genreDaoJdbc.getById(id);

        assertEquals(expected, actual);
    }

    @Test
    void should_ThrowException_When_GetByIdDeletedGenre() {
        long id = 3L;
        genreDaoJdbc.deleteById(id);
        assertThrows(LibraryDataAccessException.class, () -> genreDaoJdbc.getById(id));
    }

    @Test
    void should_UpdateGenreName_When_Update() {
        long id = 1L;
        String expected = "Test";

        genreDaoJdbc.update(new Genre(id, expected));
        String actual = genreDaoJdbc.getById(id).getName();

        assertEquals(expected, actual);
    }
}